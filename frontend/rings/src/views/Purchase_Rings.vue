<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useCart } from '@/services/useCart';

interface Ring {
  prodId: number;
  image: string;
  name: string;
  description?: string;
  materialTypes: Material[];
  bandWidths: Width[];
  ringStones: Stones[];
  basePrice: number;
}

interface Products {
  prodId: number;
  name: string;
  description: string;
  basePrice: number;
}

interface Stones {
  id: number;
  name: string;
  multiplier: number;
  Inventory: number;
}

interface Material {
  type: string;
  multiplier: number;
  Inventory: number;
}

interface Width {
  size: string;
  multiplier: number;
  MatUse: number;
}

const rings = ref<Ring[]>([]);
const products = ref<Products[]>([]);
const materials = ref<Material[]>([]);
const widths = ref<Width[]>([]);
const stones = ref<Stones[]>([]);

const selectedOptions = ref<Record<number, {
  materialType: string;
  bandWidth: string;
  ringStone: string;
  quantity: number;
  proposedPrice: number;
}>>({});

const { addToCart: addItemToCart } = useCart();

//find what material is low and store it to be displayed in UI
const lowmaterial = ref<string>('');
const findLowMaterial = () => {
  materials.value.forEach(material => {
    if (material.Inventory < 10) {
      lowmaterial.value = material.type;
      console.log(`Low inventory for material: ${material.type}`);
    }
  });
};

const lowstone = ref<string>('');
const findLowStone = () => {
  stones.value.forEach(stone => {
    if (stone.Inventory < 5) {
      lowstone.value = stone.name;
      console.log(`Low inventory for stone: ${stone.name}`);
    }
  });
};

//fetch products (ring base styles)
const fetchProducts = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/products`);
    products.value = response.data;
    console.log('Fetched products:', products.value);
  } catch (error) {
    console.error('Error fetching products:', error);
    // Mock data
    products.value = [
      { prodId: 1, name: 'Classic Band', description: 'A timeless design for everyday wear.', basePrice: 299.99 },
      { prodId: 2, name: 'Solitaire Ring', description: 'Elegant single stone setting.', basePrice: 349.99 },
      { prodId: 3, name: 'Custom Engraved Ring', description: 'Personalized with your message.', basePrice: 399.99 },
      { prodId: 4, name: 'Classic Solitaire', description: 'Traditional style with elegance.', basePrice: 449.99 }
    ];
    console.log('Using mock products:', products.value);
  }
};

//fetch stones
const fetchStones = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/stones`);
    stones.value = response.data;
    console.log('Fetched stones:', stones.value);
  } catch (error) {
    console.error('Error fetching stones:', error);
    // Mock data
    stones.value = [
      { id: 1, name: 'Cubic Zirconia', multiplier: 1, Inventory: 100 },
      { id: 2, name: 'Semi-precious', multiplier: 1.5, Inventory: 50 },
      { id: 3, name: 'Lab-Grown Diamond', multiplier: 2, Inventory: 20 },
      { id: 4, name: 'Natural Diamond', multiplier: 3, Inventory: 10 }
    ];
    console.log('Using mock stones:', stones.value);
  }
};

const fetchMaterials = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/materials`);
    materials.value = response.data;
    console.log('Fetched materials:', materials.value);
  } catch (error) {
    console.error('Error fetching materials:', error);
    // Mock data
    materials.value = [
      { type: 'Gold', multiplier: 1, Inventory: 100 },
      { type: 'Platinum', multiplier: 1.5, Inventory: 50 },
      { type: 'Palladium', multiplier: 1.2, Inventory: 30 }
    ];
    console.log('Using mock materials:', materials.value);
  }
};

const fetchWidths = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/widths`);
    widths.value = response.data;
    console.log('Fetched widths:', widths.value);
  } catch (error) {
    console.error('Error fetching widths:', error);
    // Mock data
    widths.value = [
      { size: '2mm', multiplier: 1, MatUse: 1 },
      { size: '4mm', multiplier: 1.5, MatUse: 1.5 },
      { size: '6mm', multiplier: 2, MatUse: 2 }
    ];
    console.log('Using mock widths:', widths.value);
  }
};

const fetchRings = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/products`);
    console.log('Fetched rings:', response.data);
    rings.value = response.data.map((product: Products) => ({
      ...product,
      materialTypes: materials.value,
      bandWidths: widths.value,
      ringStones: stones.value
    }));
    
    rings.value.forEach(ring => {
      selectedOptions.value[ring.prodId] = {
        materialType: ring.materialTypes[0]?.type || '',
        bandWidth: ring.bandWidths[0]?.size || '',
        ringStone: ring.ringStones[0]?.name || '',
        quantity: 1,
        proposedPrice: ring.basePrice || 0
      };
    });
  } catch (error) {
    console.error('Error fetching rings:', error);
    // Mock data
    rings.value = [
      {
        prodId: 1,
        name: 'Classic Band',
        image: 'data:image/jpeg;base64,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',
        materialTypes:  [
          { type: 'Gold', multiplier: 1, Inventory: 100 },
          { type: 'Platinum', multiplier: 1.5, Inventory: 50 },
          { type: 'Palladium', multiplier: 1.2, Inventory: 30 }
        ],
        bandWidths: [
          { size: '2mm', multiplier: 1, MatUse: 1 },
          { size: '4mm', multiplier: 1.5, MatUse: 1.5 },
          { size: '6mm', multiplier: 2, MatUse: 2 }
        ],
        ringStones: [
          { id: 1, name: 'Cubic Zirconia', multiplier: 1, Inventory: 100 },
          { id: 2, name: 'Semi-precious', multiplier: 1.5, Inventory: 50 },
          { id: 3, name: 'Lab-Grown Diamond', multiplier: 2, Inventory: 20 },
          { id: 4, name: 'Natural Diamond', multiplier: 3, Inventory: 10 }
        ],
        basePrice: 299.99
      },
      {
        prodId: 2,
        name: 'Solitiare Ring',
        image: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxITEhUTExMWFRUVGBYXGBgVGBkaFhgXGBYXGBcXGhgdHSggGBolGxUYITEjJikrLi4uFx8zODMtNygtLisBCgoKDg0NDw0NDzcZFRkrKysrKysrKysrLS0rKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIAOQA3QMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAAABQMEBgIHAf/EAEEQAAEDAQUEBwQJAwQCAwAAAAEAAgMRBAUSITEGQVFhEyIycYGRoUJSscEHFCMzYnKC0fAVkuFDY6LxwtJTg7L/xAAVAQEBAAAAAAAAAAAAAAAAAAAAAf/EABQRAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhEDEQA/APcUIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgEIQgFxJIAKk0HNV7fb2RCrjnuG8/sOax1rt81ql6OOp7sgB3/ADQaK17RRMNBVx4DM/4UUd42uX7qFrG+9IT8B/lcWO7oLMAX0fJ8+Q+a+Pv5znFsba01O4UpUZanNBfjs9qPbnYOTI/mSpPqk26evewfIhJbVeFoplDI7uYfTeqbb/eDR8U7T+R4+SDTdFaB7THd+Jv7r50toGsbXdzx8wFmf69TRkw78Y+K6ZtE4aF3igfT3z0f3kUjRxw1b5tqpbJftnk7EgPisjbtoLW4/ZuAHcHfzyWQve45rS/pHSuY/wB6MBtfFtD51Qe2NladCF2vD7tuy8o3ZXmY2t/+VglB86Eea19htV7NHVfZrQP1RE+eMIPQULJx7S2pn39hlA3uiwyDwwmvoFdg2vsp7Tnxn/cjc35IH6Esj2gsjtLREf1t/dWo7widpIw/qCCyhQG2R++3+4LplpYdHtPcQglQvgK+oBCEIBCEIBCEIBUrwtpZRrBikd2W7vzO4BUtqtprPYIelndQE4Wje53IcOayuz20DJpemFoaOkzoTUEbhUCgA05IPm01pe2VsJdWSQgEnieA4AJ9G6OxsbDGKyvBJcRnlQFx5AmgCz9+WZ0c77S9n2gBMcgdVjQaULWUzdlSpr3LW3TZOkbHJLHhe1gaMXbpvLuFTnTzQRWG7S/rvqAc/wAR/wAJ3FC1ugAXYX1AIQhAKCaxxu7UbHd7QVNVLrxv2zQ/eStB92tXf2jNBzNcMB0Zh7v2NQllo2XOrJDyBy9VA7ap8v3EeFvvy7+5g+ZXDZQ81mnL6jRpoP7Rl5oK77tmzwtc6nKoP7ruKOWLWIt40GSsy3xE2jRUDShfw5DRTwXq2mVfBxKDiz32W7q+NPimcd6Bw+7B8QoGW6F2T8vztB9V9tN3RUxNaf8A6+sf7dT3BBzaoLNJ95ZY3cyxrvlVJrVsjdcoJbC1hG6JzoyP0inwXHTN4kNOhIc06gaOGR1PgU0stwYhUygjcQKk141QYi1fR1Z3PFJbYxp4PJHq4ZLM7RbJ2+7JDaLNapJoGUc4OcSRvLXNJzByGIVpXOmq9bGyXWxGU04AZHwJVba+xmOxTtc8Fr4jABTr/aB0YNd/bBPAMKC9Zr0jNmZLiwPLQct5pvG9M7mvFs8eMaglrhwcPlQg+K8vm6R7Wsja5xoKNAJPJaj6J43fVZHONS+Zx7qNa0s/SW0PMFBuEIQgEIQgEIQgxv0jXB9ajYSA4MxZEYgCdCWnULAXXsFKYumgLWuFRJE2uHE3LE0kktrQO35HVerbXXyLJZny5YiWxsB0MjzhbUbwCankCk+zs/RiJ1SWyxtx198AAuy4mvqgz2zl8SN+zlbj6J1S12ZaRvC9Ahv+zGn2rWlwqA40PqkG2+zsj6WuxlrbTGK0PYlaM8Dhv5HmeKz1y3syaPpmx0aHYLRZ3CroJd+RHZNKjy3IPTo7VG7R7T3EKQPHELGtdE0tc0OzzGBtRTwoE4sdqeR92afmwnyzQNbRa2Mzc4BZ68NsGtyijc87q6eQz+CYTxtd2oQ78z6qs62NaaNiApvFKBBkL1vC9bRlHG5rDvxNiYO8nrHy8lnodkbZI7FNO2MbxECSeWM5u8gt7bLaXGlS8nRrP5mrdkuOR/WlcI2+63tU5nQIMfZbsZCMIMj+OJxp5JzZLunf2IjTnkPM0Tqe97vsgzewEb6gnz3LO2/6YrvYSGuLqb6Ej/gHDzIQN4tlJDr0TfAuKsDZaQaTNH6P2cqN1bZT2kB0NjtNDoXxMa3zdM0050TqKW8Dnghbydir40cR6lBSfc1paMiyQcKkH1FKr7Da3xENkY5vAn4VGTvNMumto1ihd+WRwPq0qKa9JKUlscpH4Sx4+IQSEQ2hpa8B3OlHNSeVs1icHCr4z7Q0PAOG456jI8lFNbIWGrBLHQ1pIxzacS15FPAmnwV2xX5G+rHFkkbgQaFuXGra6dyB1dt5MmbVpz3jeFk9urZ0kkdnB6rDjf8Amp1R4An+4Ltohs8he2fCzUb314U0PeeKWEPtspMDCGb3ns8yXe0eQ4+QNtmg1sck7hQCvkMgB8Anuy1j6KzMbQCtXEDQFxJoPMJeYGu6OzR5sbTGfew6+HxK0zRRB9QhCAQhCAQhCDyn6abaTNYLONDI+V3CrWFjfHrup4ptabQWuDBWgLY95FKZnkBUHkuNvLKyW1EPFTFA2dvAYZHtcf7XOyXFvvYQPfWIPbJjGoyrw5Uw+SDY3XaSGhj+Ya7caGhHesxtlsjKJDbrvo20gUkjNOjtLPceDlXgU/u+3RusfTS0YwB73E+zRxq6vr4qDZzaWOdg7QzOHGKOIBoDT+aoMldl5scAATFp0kTqh9nkPaaQQCWVBod9KrU2Sw2giomZQ6HUU41TC9bks1pFZGNLqZPblIByeM6ctCksWx8kVRBbpGDg9od6gtHogZvup57dpp3NH7rk3VZmjFJKSB7zwB6Kp/QLWe1eLqfhjaD5krpmzVkZ1p3PtB/33Fzf7Oz6IK0V6tL6XfE6bc52QgHfKfg2p5KaewPd1rbaC6ukMNWRjli7Tzzy7uN603plhjAAHLIDuSmQ16zjyqfgECi+S0/ZWWxxDjI9ooO6oJcfTv3Z+x/R+3Hje5tTmQ2Ma/mOfotlHaIvfa3ma/AfOils053sbMzjC8gj9ORQMLrmdFExlcWAYanWg09Fd/qruHoqtjnsEhoY42v4SsGLzcM/NMjc1mP+hF4Mb+yCFl7V3BTNvIcB5riW44CKAOaNeo97fQFVn7OitWzSt5dQj1bn5oLzre3hXxCp2uGCTt2dr+8MPqVG65pRpK0/mYQfMPoP7UuvGSSz0MgGE1ALHhxNBU9VwafKvggH7P2KtRZG5cZHAeQqCORFFekkLWYata0CgYwUFOFa18qKMwy5UqQc+yRqOBAWfn2ngs7y+eaMdG4gsrVwwmhJqKE5tIpod5Qa+5IQMVcpK9YEULR7Ipwp/Mk2WOZtHFM+O02c42dl5GhYe45EHitgCg+oQhAIQhAIQhBmdoIB9YFR1ZbPNET+IdZv/AyrG3m100TMIqabvZIAa8HhRwIW52zs0jrOXROwyxnEx1K0NCNOHHlVeI2S+7VG50LWNAkcCYZmTuIkIIdgkiYQ5hPWzpSqDUXoy1PZZ7M4Ftnbie8CtXvDmloJPs0dUDjXgFrbiu6JsQmkAoW4IxXTe5438hyb+JZq7Lpve0tBfNFZ27mluNwNCCcRcaZHh4Bae7dhiC02i3WiagAwteImim6sYaS2m4oJYbza0gOkABIDS40JqaAHnXh6Jk+SQcfDP4K3d9x2eEkxxgE6uJLnHfm5xJ9VZtEAcDkwnnl6oM9abcG9p5zNKAEuJ4BoBcfAKOIyP7MMp5uaWjzd+1VWvh77JEWuZI+AOxfZHG4Z4jrmc9BR2gyyVfZ36RLPaHmOKZtopo2hjmOeuF4aH9zaoNHZ7oec30b6n9vGvgrktyQubRza/iqcXnuHLTku7uvaKaoY6jhqxwwvb3tOavIMpbdkzrE+vJ2R8wkNquwsPXY5h47vAj5L0pcSRhwo4AjgRUIPK70ss8zMDbQRShBwte4U780wuP8AqMQDWzxSgbngsNOGpC1dt2cjdmz7N3m3y/YpJbLJLEQHivBzSgZsvu1N+8sjjzjIcEDbCIZOjma7gY3V9Akr796EZucTuG8ncO5Ln3hJPIGvcS45thjzd3mmTR+J9P0mlQ0lu2qLhhs7KuPtP7La6ado8t/qo7nuVz3iWZxc7e52p+QHIZDhUq3dlyBnWlo0a4B64nb68Bluz1Vm23jTqs7sv5/OaC9abc1mQ6x4VyrzP/ZWTv8As0c46+Wdfs6MdUadehdkeBCbQXfLJm7qN56n9P7kLL3u+eG2PYXxdBG2z1xMq8meSRgp1v8AbOQFUEFz3FHZ3OdGXVeauqScR400XptmHVbXgPgsLtLKYrK20QjCXUFHZgE5A78q6Z6kc1vWGoB4hB0hCEAhCEAhCEGf22vn6rZZHiPpXuBaxlaAkjMuO5oGZKy90OEjWytjccYByaSeeg0rUeC0G10Bc5ldMJArxrn6UX3ZY/Y9CGtrEKMqMsO4ZeXcgqG0OYM2OHAEEHwbr6JhFZpz7FO8jPnqkd5WVzJHjMkurrQ0eBnlqKsGZ4U3EK/Y9ubMwFlqf0UjCGuxA0zaHtrlkS018DwQMRYp+A8wuXCZuZa8d3W+FVasm0ljl7Fpid+sV8imDLQw6Oae4goEn1nEKVqvONrfo9D5xbLERFOHB5Y4fZSOGdR7rjv3HXI6+v2iOJ3bw95ND5pNbBG3syNd+Goqgxd17TRy0itrDZ5mdXpCaAHfhk1aOUgGu/Vauz221R0NW2iM6OBo/PSu46jME7/FTe1yWe1doEPGQcMnAbqjRw/gSCLZy22Uk2S1Ubrhd2dfdNWnxy4Kj0WHaJhIDo5Gk11bw35H4VVS37QSl2GKMtG98jXHyY0VPjRY2a9LeaCeyNfT24ZHMcfBrhT17lNZnwyOaHx2+ImgqJHuaN2bjnTmoH1qvYsB6SWSuuoZlTMhuVBzdTvWZn2qMjiyzY7Q85HoR0prTe8kRs8XcaYlo4di7trikiMprWk0kkra8QxxLQfBaGCaKJuGNjWtGgaAB6IMLduw1rmditEgs0ZzLYXYp3DeHTEAMBzrgFdOstpd132axswQMa0akjMuO8udq53MrqS2PfXCCe4JV9YGP7QGnun5jePRBM+9WSSYDIGjeVoLLZWNAwgd+pPOqzN8XC2cGWzkMl1p/pycnDd365pZcO0T4SWSNcGtOF7HdqM7iOLTxQehLy6/nmW8ngYcLXtj1BcXNhFHYa1wtdM4VI7R14+jS3hG2IzYhga0vryAqvNdgLG+Wd1pl7T3ufQ7sbsVPMNH6Sg3O0NlH1Qxj/baOfWaB6p20JdeYxOij4vDz3R9b/8AQamSAQhCAQhCAQhCDHfSBtA2zGyRYA99ptMbKHMtYD13036gdxPBWbTN0VoLgNCAc64m0z8qeVSs79IF3GW8LLMDnZDE8190yHGAOJPR57sKsX3M4GR5PtFudNC1zdaZa7v8INHtLYy+MTRgudGKkDV8ZFXAfiGo7iN683eQ2045cM0cmE4nAOBaQKHhkOAyXp1zPdGGxyHVoc08agVb3g/JZzabZ/oy57W4oHElzRrC52bnD/bJzPukn2T1QquhsrifsQw0IrhGVctE3uOGxxxtbha5w1cQCTn3pDd9mxUjJzGTSKnE3dlxHKu5XpLuDa0lYSBWgOdNEDb+kWUydJU0r2HZtKvtmszNGMHc0fskFiua0vaHABoOYqaeiut2blpV8zQOQPxQfbdeEZ9keQH+Uujt7iaBuLlnXz8FbluiFpwmR0r9zI6V8eATCzXKMNbQRhH+mMmUy7Z9s5d3eoKNkZ00eNjScyMswaalp0cN1eISa/L9s9jIFof0Zdm0FryXU1pRpzzHmE4vnaB/3VkjDnjLG4UjZ/7Ee6OGZCz8OyjXv6a1yOmk3knC0DhRtKN1yOSoebM2+O2tL4nODWuwnGxzTWgOVdRmMwtFFdzBq6vlT5pHYIQxuCCJ2EaYG0ZX82h76lTyQTjPon+BafQOqgaW27GyZte6N4oMbCa0G6laUzO5UbVY5mto8fWWdwbKOYIpX4qlHeZBwklrvdeCHa7gRmmMN6nf65/5QI7HfEbJCzrtpmWyCjgOPDx804vO7I7U1srMPStHVeNHNPsupqMvAqa1PimFJYmvA00qMtaOoW+BKS2vZiyu7D7RCSc6BzwPFzXAd5UCeKWscsBkAYc+ja4F9QSCBn2cQA8ORTbYYZEjsR5Fw0Lt/eAMq8cSqHZCz9k2q0PZvYxjWgjcCWRiniQEye5kUbYYwI425BpI46vOlN9B48BQ7u13SSPm3dhteAzJpzP8yomqrXfG1sbQ0hwpqDkScyfEqygEIQgEIQgEIQgwG1Eb/rVrd7LbJDIOTmyPP/j6KK+yDFORucDXP2sRHyWmvayh0z2kff2Z8debDUDvpI4/pWSlnLo8OX2jGHraYgMLwcxo4HyQb6SzCSNu40aWngaZFQWK8dWSZPbkfke4jeqX9djs9ljc9wLg0NABzJaKeWXqsvY55rRL9YkkMTG5AUqSNwDaVIr6oNVbdmopDijPRu1GEVbU51oCCOPVIzzUD7imyOKN54vbnTvFK+NVHdN4GRpdG6oa4sJzaCQAatrkRQjfkajOlVf+vSDcfKo80H1tltZoDJGAKCrWuxd+tPRBuev3s73cgcAPln60UL7wdxVF94NccIdicMiBV1DuDqZN8aIGwtUMIwxMHgPid6VWud8lS93V8gAp4bG9w7Bz97qgc3b/AAAPgmFmuZmshxu4aMb3N+ZqUGWmvFjMmAHnTLwH803r5BeUdQ5znNcPeAeyvdTJaO2bMwPzb1Ty08kktezErM20ePVBYk28s0LmMnkYMeTXMOR7xuWjsV6QygFkjXVXnNuutpymhB/M0EKvYroiYawvfF+EGrPBp08EHqtos7Hij2hw4EApZLcEfsFzOWrfI/I1SWyWyZjQG2hp/O30qpn3peFKsZDJl7xHrxQSPsMzHULC5vvR0cO4sJDq6ZjnkN8f1sY+jDxiz6pD2uypXJzRxCztg2gvC0SlnSMaOycHWa08MftGnBbO57mjs4MjnOLiMy48TU0HM0J45IKR6Q6Ncf5/NFmtqrunkYWxuFcsXWLQCCCCXjWlOy0145LX3reYoc8LRrmBkNS47hy8+Cy1lvdk76R9dor1hTCKaj+ckE+wEc1lAhklMjXHQknCTuBJJppqV6CsvcNkc6Q4wGujcagEOB4EHhxrvBWoQCEIQCEIQCEIQKNow5rGyt7UTw7vaatcPEFeJTXlbTLJHZ4zgLnuDJIZXUxOOkgIGHQjNew7X7SQWWNzZA5xc09VoJNDXyWe2ftYljbJG0lrqnmCDQjvBBBQZ+6tm73lADpYbO3fhjaXGo955fTXhxT67/ossQcJLW42qXe6VzqHkQXUpnpkO5PJrW5g6wwjnkiFk7+yx1OJo0U4ipr4FA3uyx2WzRiKBkcUYNcLAAKnU03nLVSvMB1DfIJV/S7Qd8Y8XV+FF3/S5qasJ7yPkg+XlZcQ+x6OvBxc015PGKn9pXndr2vku1sVltkUvR4iYrRGI8usa1DKDEMRrQAkGtF6BJZJW6tPeM/hp4pbeVhZPG6KVrXsOrXCtDx4tcgtXdtFJJE2aLDaYiK4mVY/Cd/RnM+mm9OLuvaOYVY7Pe05OHeCvK7JdlpuqQyWQOkhObotWnjVmrXU0cygNMwtldtqsV4jHC8w2gCjh2ZGkfhOTh6eoQa7GjGVmzPb7Pk9gtDB7TD1qb6tOdf5mvg2nY7L7t28PBBB79PUKjQy0OoB70ntt3WZ2dA08WmiqPtuL/UB7nA+gKWXpewibUinN5wgbhrQ5qDm84Gxioky/EPmksUstocIY8XXFcLe04V1zyazi52XCqju6yW28JDgjLIgSDPO2kYG8wQn7x3BzsqbjVek3Jc0NjjwsqXHN8jzilkd7z3Uz5AZDQAIIdn9n47KwE0xgajQVzIFdTlqczTcpbTO6R2FmfwbzPNRW63F2Q0Rd9pLMtx1QUbds5PJ7Y5a+vFZHZawGzyyWeoAjmlDgKE0IbLmdTqKL1eGYOFQvNop2mWefdJI+U8cDAWMp+YAd5ooO7gvat75uqZWSMdnujALRTgCD5r0teRbEXPJHegfKKuLZCCMw1tG9WvEl9T3BeuqgQhCAQhCAQhCDHba3P0hL6YmuaWu5ZUz5UVjZqyxmxizBoDWNwgDKg5LTubVILVD9XlEjPu3GjwNB/j4IM3ZCLLKSGkguqSSTQigyB3kADdmDvW8sdpZKxr2GrXCoPy71mtqrGKdM3sOpi06rvZf3HIHhkaalIdlLbJZ7Q+Fx+zeS4D3X6mnIg5+aD0rCvpS0yO4rnpHcUDMlVrQyN3aAPf+6pkuXBYUFa12YDON1fwu+Ad+9e9I7fc0MxDnMLXjMEEtkHMOaeW4ndxWjMajkjFM9OaBPYbfbIMjIJ2DQSij+7pGjPvIOqfw3pDKOuyh4PAKUNkbJ92cQqKuoSylesA7eaV0rQ0rSqSX3tJZbNO2zukDpX6MYKndQE1FCa5DkeCDZPsFjd7LR3Et+BX2KxWRjg5sTMTeyaVI7idD3ZqpZrulc0Etw13OIqO+lVbjup29w8/8IJZbw90Ku2J8pq6ob6nu/c+u6uOmiJxWcvFT1muBoK5dXWvcCujtCB2oZWjiWmnfpX0QWJrHhGQyVQsG5SN2ms+94Z+ctb4UJxeiVXltHZwSWnFTXowXfAVA50IQWbzthjicRWp6tQNMWVe/gkF02TpJGx6AYXvABADWH7NnKpzP5RzXDrfbLVUwQOa0AkPmBjY3QEgOzLqGuI7ge5NrqsjoIsGLpJnnryCuZO4V1ppXQd9UDq548U0jxowdGOZJq8+dB4J6FUuuziOMMGozPeVcQCEIQCEIQCEIQcyOoFTkaHAg5g681YtGiqkoKVkk6MmKTNjq0rmC05UNdeaSXvsw9pD4Os0UIbq5tDkPxt/5Dnu1ctlbIyh7wRqDxCV9PJAaO03Hcf2PJAuu+/2tAbKCwtFK0JGXHeN2orXcncFua7sua78pBPI04c18+tQyZyMaTlmQDp/2q0t0WJ3shvMEg1PPVBcfNyPkonzgCpyHE5CnGqpG5LMNJpAOGNxCiN0WIGry6T8xJ+KDp18xk4Yj0rvdj63mRkPEqdl24hjtZGHURDNg4YvfOWmnehtubGMMETWDjl8P8qhaA5565Lq/ygA0QV782olfWKwsBOnSOyjb3U7Z1yGQ56LPWHYzE/pbRI57yammQr8a/JauKKmQFO/9lYMfGp8UE4trhlXRTR3md6Xuib7oUZgZwI7nH55IHLb1HP0Uwt4O8eP/AGkTbOz3njxH7KVllG57/IfsoGzrZyZ5k+lAq0tuO6g5hor5mo9FXFk5uPfQKaK7uJNOX7qiEuc47yeZr/geCuWWxYTidm74K1BCG5NH87109B9idmrSpsCtgoPqEIQCEIQCEIQRWgZKs4K48ZKqUE0Oi+TsaQQ6hB3FRMcdAuhDxQJbVd9D9npwPyVN4e3VjvAVWn6JfCxBlmzHTC7yIVuCxyO0AHefkE8wL5gQUYro94k8hkr0VjDey0D4+aka8hSCZBCbNXUDyUbrAN2SvNcCvqBU+7z3r4LCOCarh0gQUW2McF30YG5SkkrpsaCJrFM2NdmgXLX11QfQ2vIKOSOisBcT6IIwMlNHoomqWPRB0hCEAhCEAhCEAoXw1UyEETYqLog8l2hBHhPFGA8VIhBGI0GJSIQQmFAiKmQgh6JfcB4qVCCIxk718EKmQg4bGuivqEEZjXPQqZCCNrCF1I2q6QgjEa7aKL6hAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhAIQhB//Z',
        materialTypes:  [
          { type: 'Gold', multiplier: 1, Inventory: 100 },
          { type: 'Platinum', multiplier: 1.5, Inventory: 50 },
          { type: 'Palladium', multiplier: 1.2, Inventory: 30 }
        ],
        bandWidths: [
          { size: '2mm', multiplier: 1, MatUse: 1 },
          { size: '4mm', multiplier: 1.5, MatUse: 1.5 },
          { size: '6mm', multiplier: 2, MatUse: 2 }
        ],
        ringStones: [
          { id: 1, name: 'Cubic Zirconia', multiplier: 1, Inventory: 100 },
          { id: 2, name: 'Semi-precious', multiplier: 1.5, Inventory: 50 },
          { id: 3, name: 'Lab-Grown Diamond', multiplier: 2, Inventory: 20 },
          { id: 4, name: 'Natural Diamond', multiplier: 3, Inventory: 10 }
        ],
        basePrice: 349.99
      },
      {
        prodId: 3,
        name: 'Custom Engraved Ring',
        image: 'data:image/jpeg;base64,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',
        materialTypes:  [
          { type: 'Gold', multiplier: 1, Inventory: 100 },
          { type: 'Platinum', multiplier: 1.5, Inventory: 50 },
          { type: 'Palladium', multiplier: 1.2, Inventory: 30 }
        ],
        bandWidths: [
          { size: '2mm', multiplier: 1, MatUse: 1 },
          { size: '4mm', multiplier: 1.5, MatUse: 1.5 },
          { size: '6mm', multiplier: 2, MatUse: 2 }
        ],
        ringStones: [
          { id: 1, name: 'Cubic Zirconia', multiplier: 1, Inventory: 100 },
          { id: 2, name: 'Semi-precious', multiplier: 1.5, Inventory: 50 },
          { id: 3, name: 'Lab-Grown Diamond', multiplier: 2, Inventory: 20 },
          { id: 4, name: 'Natural Diamond', multiplier: 3, Inventory: 10 }
        ],
        basePrice: 399.99
      },
      {
        prodId: 4,
        name: 'Classic Solitaire',
        image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQx--tC3dy820ZF3LN3Vz3G71dAIQqvUwL2w&s',
        materialTypes:  [
          { type: 'Gold', multiplier: 1, Inventory: 100 },
          { type: 'Platinum', multiplier: 1.5, Inventory: 50 },
          { type: 'Palladium', multiplier: 1.2, Inventory: 30 }
        ],
        bandWidths: [
          { size: '2mm', multiplier: 1, MatUse: 1 },
          { size: '4mm', multiplier: 1.5, MatUse: 1.5 },
          { size: '6mm', multiplier: 2, MatUse: 2 }
        ],
        ringStones: [
          { id: 1, name: 'Cubic Zirconia', multiplier: 1, Inventory: 100 },
          { id: 2, name: 'Semi-precious', multiplier: 1.5, Inventory: 50 },
          { id: 3, name: 'Lab-Grown Diamond', multiplier: 2, Inventory: 20 },
          { id: 4, name: 'Natural Diamond', multiplier: 3, Inventory: 10 }
        ],
        basePrice: 449.99
      }
    ];
    
    rings.value.forEach(ring => {
      selectedOptions.value[ring.prodId] = {
        materialType: ring.materialTypes[0]?.type || '',
        bandWidth: ring.bandWidths[0]?.size || '',
        ringStone: ring.ringStones[0]?.name || '',
        quantity: 1,
        proposedPrice: ring.basePrice
      };
    });
  }
};

const updatePrice = (ringId: number) => {
  const ring = rings.value.find(r => r.prodId === ringId);
  if (ring && selectedOptions.value[ringId]) {
    const options = selectedOptions.value[ringId];
    // swap the price multipliers with multipliers from db
    let price = ring.basePrice;
    // use fetched values for attributes to calc price
    const selectedWidth = ring.bandWidths.find(m => m.size === options.bandWidth);
    const selectedMaterial = ring.materialTypes.find(m => m.type === options.materialType);
    const selectedStone = ring.ringStones.find(m => m.name === options.ringStone);

    if (selectedWidth) price *= selectedWidth.multiplier;
    if (selectedMaterial) price *= selectedMaterial.multiplier;
    if (selectedStone) price += selectedStone.multiplier;

    options.proposedPrice = price * options.quantity;
  }
};

const addToCart = (ringId: number) => {
  const ring = rings.value.find(r => r.prodId === ringId);
  const options = selectedOptions.value[ringId];
  
  if (ring && options) {
    addItemToCart({
      ringId: ring.prodId,
      ringImage: ring.image,
      materialType: options.materialType,
      bandWidth: options.bandWidth,
      ringStone: options.ringStone,
      quantity: options.quantity,
      price: options.proposedPrice,
      addedAt: Date.now()
    });
    
    alert(`Added ${options.quantity} ring(s) to cart!`);
  }
};

onMounted(async () => {
  await fetchStones();
  await fetchMaterials();
  await fetchWidths();
  await fetchRings();
  findLowMaterial();
  findLowStone();
});
</script>

<template>
  <div class="purchase-container">
    <div class="rings-grid">
      <div v-for="ring in rings" :key="ring.prodId" class="ring-card">
        <div class="ring-image-container">
          <img :src="ring.image" :alt="`Ring ${ring.prodId}`" class="ring-image" />
        </div>

        <div class="name&desc">
          <h3 class="ring-name">{{ ring.name }}</h3>
          <p class="ring-description">{{ ring.description }}</p>
        </div>

        
        <!-- show warning if lowMaterial string or lowStone isn't ''-->
        <div class="Low-inventory-warning" v-if="lowmaterial || lowstone">
          <!-- Return what specific material is low -->
          <p class="warning-text" v-if="ring.materialTypes.some(m => m.Inventory < 10)">Low Material Alert!</p>
          <p class="warning-text" v-if="ring.materialTypes.some(m => m.Inventory < 10)">{{ lowmaterial }} is low!</p>
          <!-- Return what specific stone is low -->
          <p class="warning-text" v-if="ring.ringStones.some(s => s.Inventory < 5)">Low Stone Alert!</p>
          <p class="warning-text" v-if="ring.ringStones.some(s => s.Inventory < 5)">{{ lowstone }} is low!</p>
        </div>

        <div class="ring-options">
          <div class="option-row">
            <div class="option-group">
              <label>Select Material Type</label>
              <select 
                v-model="selectedOptions[ring.prodId]!.materialType"
                @change="updatePrice(ring.prodId)"
                class="option-select"
              >
                <option v-for="type in ring.materialTypes" :key="type.type" :value="type.type">
                  {{ type.type }}
                </option>
              </select>
            </div>
            
            <div class="option-group">
              <label>Select Band Width</label>
              <select 
                v-model="selectedOptions[ring.prodId]!.bandWidth"
                @change="updatePrice(ring.prodId)"
                class="option-select"
              >
                <option v-for="width in ring.bandWidths" :key="width.size" :value="width.size">
                  {{ width.size }}
                </option>
              </select>
            </div>
            
            <div class="option-group">
              <label>Select Ring Stone</label>
              <select 
                v-model="selectedOptions[ring.prodId]!.ringStone"
                @change="updatePrice(ring.prodId)"
                class="option-select"
              >
                <option v-for="stone in ring.ringStones" :key="stone.name" :value="stone.name">
                  {{ stone.name }}
                </option>
              </select>
            </div>
          </div>
          
          <div class="bottom-row">
            <div class="option-group quantity-group">
              <label>Quantity</label>
              <select 
                v-model.number="selectedOptions[ring.prodId]!.quantity"
                @change="updatePrice(ring.prodId)"
                class="option-select"
              >
                <option v-for="n in 10" :key="n" :value="n">{{ n }}</option>
              </select>
            </div>
            
            <div class="price-group">
              <label>Proposed Price</label>
              <div class="price-display">${{ selectedOptions[ring.prodId]!.proposedPrice.toFixed(2) }}</div>
            </div>
            
            <button @click="addToCart(ring.prodId)" class="add-to-cart-btn">
              Add to Cart
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.purchase-container {
  padding: 20px;
  width: 100%;
  box-sizing: border-box;
}

.rings-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.ring-card {
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

.ring-image-container {
  width: 100%;
  height: 250px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  box-sizing: border-box;
}

.ring-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.warning-text {
  color: #ff4d4d;
  font-weight: bold;
  text-align: center;
  margin: 5px 0;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8);
}

.ring-options {
  padding: 15px;
}

.option-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin-bottom: 10px;
}

.option-group {
  display: flex;
  flex-direction: column;
}

.option-group label {
  color: white;
  font-size: 12px;
  margin-bottom: 5px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8);
}

.option-select {
  background-color: rgba(209, 209, 209, 0.9);
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
  font-size: 14px;
  cursor: pointer;
  color: #333;
}

.option-select:focus {
  outline: none;
  border-color: #baaa51;
}

.bottom-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1.5fr;
  gap: 10px;
  align-items: end;
}

.quantity-group {
  min-width: 80px;
}

.price-group {
  display: flex;
  flex-direction: column;
}

.price-display {
  background-color: rgba(209, 209, 209, 0.9);
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
  font-size: 14px;
  font-weight: bold;
  color: #333;
  text-align: center;
}

.add-to-cart-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 38px;
}

.add-to-cart-btn:hover {
  background-color: #45a049;
}

.add-to-cart-btn:active {
  transform: scale(0.98);
}

@media (max-width: 1200px) {
  .rings-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
}

@media (max-width: 768px) {
  .option-row {
    grid-template-columns: 1fr;
  }
  
  .bottom-row {
    grid-template-columns: 1fr;
  }
  
  .add-to-cart-btn {
    width: 100%;
  }
}
</style>
