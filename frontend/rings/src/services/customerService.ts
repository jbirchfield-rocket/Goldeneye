// Customer service for managing customer ID in cookies

export const getCurrentCustomerId = (): number | null => {
  const match = document.cookie.match(new RegExp('(^| )customerId=([^;]+)'));
  return match ? Number(match[2]) : null;
};


export const setCustomerIdCookie = (customerId: number, maxAge: number = 31536000): void => {
  document.cookie = `customerId=${customerId}; path=/; max-age=${maxAge}`;
};

export const clearCustomerIdCookie = (): void => {
  document.cookie = 'customerId=; path=/; max-age=0';
};
