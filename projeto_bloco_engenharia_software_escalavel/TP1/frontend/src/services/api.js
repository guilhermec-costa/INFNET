const API_URL = 'http://localhost:8080/api';

async function request(path, options = {}) {
  const response = await fetch(`${API_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    },
    ...options
  });

  if (!response.ok) {
    let message = 'Nao foi possivel concluir a requisicao.';
    try {
      const data = await response.json();
      message = data.message || message;
    } catch {
      message = response.statusText || message;
    }
    throw new Error(message);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export const api = {
  listarLivros: () => request('/livros'),
  listarLivrosDisponiveis: () => request('/livros/disponiveis'),
  criarLivro: (payload) => request('/livros', { method: 'POST', body: JSON.stringify(payload) }),
  atualizarLivro: (id, payload) => request(`/livros/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  excluirLivro: (id) => request(`/livros/${id}`, { method: 'DELETE' }),

  listarLeitores: () => request('/leitores'),
  criarLeitor: (payload) => request('/leitores', { method: 'POST', body: JSON.stringify(payload) }),
  atualizarLeitor: (id, payload) => request(`/leitores/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  excluirLeitor: (id) => request(`/leitores/${id}`, { method: 'DELETE' }),

  listarEmprestimos: () => request('/emprestimos'),
  registrarEmprestimo: (payload) => request('/emprestimos', { method: 'POST', body: JSON.stringify(payload) }),
  registrarDevolucao: (id) => request(`/emprestimos/${id}/devolucao`, { method: 'POST' })
};
