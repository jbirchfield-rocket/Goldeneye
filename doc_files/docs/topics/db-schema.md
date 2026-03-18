---
description: The GoldenEye Exquisite Rings database schema enables customers to configure rings by selecting from available products, materials, widths, and stones, with automatic price calculations based on customization choices.
---
# Database Schema

## Overview

The GoldenEye Exquisite Rings database schema enables customers to configure rings by selecting from available products, materials, widths, and stones, with automatic price calculations based on customization choices. It manages the complete order lifecycle—from customer and product information through order entry, customization specifications, and inventory tracking.

## Entity-Relationship Diagram

![Database ERD](../assets/ERD.png)

## Database Objects & Schema

### Tables

#### Order

| Name | Type | Rules |
| --- | --- | --- |
| OrderID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 100, PK |
| CustID | Integer | Not Null, FK from Customer |
| LocID | Integer | Not Null, FK from Location |

**Description:** The Order table serves as the primary transaction record. Orders serve as the parent record for OrderItem entries, allowing customers to order multiple customized rings in a single transaction. Each order is tied to a specific customer and delivery location.

**Business Logic:**

- Each order must be associated with an existing customer and a valid delivery location
- Orders can contain multiple line items (OrderItems), each representing a different ring customization
- LocID links orders to a specific delivery address for fulfillment
- The order acts as the primary grouping mechanism for fulfillment and billing purposes

#### OrderItem

| Name | Type | Rules |
| --- | --- | --- |
| OrdItmID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 100, PK |
| OrderID | Integer | Not Null, Positive, FK from Order |
| ProdID | Integer | Not Null, Positive, FK from Product |
| MattID | Integer | Not Null, Positive, FK from Material |
| WID | Integer | Not Null, Positive, FK from Width |
| StoneID | Integer | Not Null, Positive, FK from Stone |
| UnitPrice | Decimal | Not Null, Positive |
| Qty | Integer | Not Null, Positive |

**Description:** The OrderItem table represents individual line items within an order. Each record details a specific ring customization including the product type, material, width, and stone selection. This enables customers to order multiple customized ring variations in a single order.

**Business Logic:**

- Each order item captures a complete ring specification combining product, material, width, and stone selections
- UnitPrice is the calculated price for the ring with these specific customizations
- Qty allows customers to order multiple rings with identical specifications
- The total line item value is: UnitPrice × Qty
- Multiple OrderItems can reference the same Order, Product, Material, Width, or Stone

#### Customer

| Name | Type | Rules |
| --- | --- | --- |
| CustID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 50, PK |
| Name | Varchar | Not Null, up to 120 characters |

**Description:** The Customer table maintains core customer information for the ring sales system, serving as the foundation for all orders and associated delivery locations.

**Business Logic:**

- Each customer is uniquely identified by CustID
- Customer names support up to 120 characters
- One-to-many relationships: A customer can have multiple locations and place multiple orders

#### Location

| Name | Type | Rules |
| --- | --- | --- |
| LocID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 50, PK |
| CustID | Integer | Not Null, FK from Customer |
| Str | Varchar | Not Null, up to 200 characters allowed |
| City | Varchar | Not Null, up to 120 characters allowed |
| ST | Char | Not Null, up to 2 characters allowed |
| Zip | Char | Not Null, up to 10 characters allowed |

**Description:** The Location table stores delivery and billing addresses for customers, supporting multiple business addresses per customer. Orders reference specific locations to ensure accurate delivery.

**Business Logic:**

- Each location is tied to a specific customer via CustID
- Street field accommodates up to 200 characters for detailed addresses
- State stored as 2-character abbreviation (e.g., "NY", "CA")
- Zip code field accommodates extended ZIP+4 format (up to 10 characters)
- Multiple orders from the same customer can be delivered to the same location

#### Product

| Name | Type | Rules |
| --- | --- | --- |
| ProdID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 50, PK |
| Name | Varchar | Not Null, up to 120 characters allowed |
| Dscrp | Varchar | Not Null, up to 512 characters allowed |
| BasePrice | Decimal | Not Null, positive |

**Description:** The Product table defines available ring product types offered by the business. Each product represents a base ring style that can be further customized with different materials, stones, and widths. BasePrice provides the starting price before customization adjustments.

**Business Logic:**

- Each product defines a distinct ring style or category (e.g., "Classic Engagement Ring", "Wedding Band")
- BasePrice serves as the foundation price for unit price calculation
- Product descriptions support up to 512 characters for detailed specifications
- Multiple OrderItems can reference the same product

#### Material

| Name | Type | Rules |
| --- | --- | --- |
| MattID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 50, PK |
| Name | Varchar | Not Null, up to 120 characters allowed |
| Dscrp | Varchar | Not Null, up to 512 characters allowed |
| BasePrice | Decimal | Not Null, positive |

**Description:** The Material table catalogs available metal and material options for ring customization. Each material option represents a different metal type or composition. BasePrice functions as a price component applied to the order item's unit price calculation.

**Business Logic:**

- Each material represents a distinct choice (e.g., "Gold 14K", "Platinum", "Sterling Silver")
- Material descriptions detail composition and characteristics (up to 512 characters)
- BasePrice represents a pricing component used in unit price calculation
- Multiple OrderItems can reference the same material for identical material selections

#### Stone

| Name | Type | Rules |
| --- | --- | --- |
| StoneID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 50, PK |
| Name | Varchar | Not Null, up to 120 characters allowed |
| Inventory | Integer | Not Null, default 0 |
| Price | Decimal | Not Null, default 1.0000 |

**Description:** The Stone table maintains inventory and pricing information for gemstones and decorative stones used in ring customization. Each stone type tracks available inventory and unit pricing for accurate order cost calculations.

**Business Logic:**

- Each stone represents a distinct gemstone or decorative option (e.g., "Diamond", "Sapphire", "Ruby")
- Inventory field tracks current stock quantity; defaults to 0 for out-of-stock items
- Price field represents per-unit cost; defaults to 1.0000
- Inventory should be decremented when orders are fulfilled to maintain accurate stock levels
- Multiple OrderItems can reference the same stone, drawing from shared inventory

#### Width

| Name | Type | Rules |
| --- | --- | --- |
| WID | Integer | Not Null, Positive, Starts with one, Increments by 1, Caches at 50, PK |
| Width | Integer | Not Null, measured in millimeters for example |
| Multiplier | Decimal | Not Null, default 1.0000, positive |
| MattUse | Integer | Not Null, positive |

**Description:** The Width table defines available ring width/size options and their associated pricing multipliers and material consumption rates. Width specifications enable precise control over pricing variations based on ring size and material requirements for production planning.

**Business Logic:**

- Each width specification represents an available ring size measured in millimeters (e.g., 2mm, 4mm, 6mm, 8mm)
- Multiplier represents a price adjustment factor applied during unit price calculation (e.g., 1.0, 1.25, 1.5)
- MattUse tracks the quantity of material required for manufacturing rings of this width specification
- Multiple OrderItems can reference the same width for different ring customizations in the same size

### Relationships

- **Order** → **Customer**: CustID (Foreign Key)
- **Order** → **Location**: LocID (Foreign Key)
- **OrderItem** → **Order**: OrderID (Foreign Key)
- **OrderItem** → **Product**: ProdID (Foreign Key)
- **OrderItem** → **Material**: MattID (Foreign Key)
- **OrderItem** → **Width**: WID (Foreign Key)
- **OrderItem** → **Stone**: StoneID (Foreign Key)
- **Location** → **Customer**: CustID (Foreign Key)
