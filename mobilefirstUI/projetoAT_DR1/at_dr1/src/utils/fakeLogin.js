export const fakeLogin = (username, password) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (username === 'user' && password === 'password') {
        resolve({ status: 'success', token: 'fake-token' });
      } else {
        reject({ status: 'error', message: 'Invalid credentials' });
      }
    }, 1000);
  });
};
