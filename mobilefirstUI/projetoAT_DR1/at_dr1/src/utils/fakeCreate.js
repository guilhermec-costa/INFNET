import { items } from "./fakeList";

let currentId = 4;

export const fakeCreate = (name, description) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const newItem = { id: currentId++, name, description };
      items.push(newItem);
      resolve(newItem);
    }, 1000);
  });
};
