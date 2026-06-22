const headers = { 'Content-Type': 'application/json' }

async function request(path, options = {}) {
  const response = await fetch(path, {
    headers,
    ...options
  })

  if (!response.ok) {
    const error = await response.json().catch(() => ({ error: 'Erro inesperado' }))
    throw new Error(error.error || JSON.stringify(error.errors) || 'Erro na requisição')
  }

  if (response.status === 204) return null
  return response.json()
}

export const api = {
  listarPerfis: () => request('/api/perfis'),
  criarPerfil: (data) => request('/api/perfis', { method: 'POST', body: JSON.stringify(data) }),

  listarUsuarios: () => request('/api/usuarios'),
  criarUsuario: (data) => request('/api/usuarios', { method: 'POST', body: JSON.stringify(data) }),

  listarEmpresas: () => request('/api/empresas'),
  criarEmpresa: (data) => request('/api/empresas', { method: 'POST', body: JSON.stringify(data) }),
  aprovarEmpresa: (id, responsavelExternoId, usuarioInternoId) => request(`/api/empresas/${id}/aprovar`, {
    method: 'PATCH',
    body: JSON.stringify({ responsavelExternoId, usuarioInternoId })
  }),
  reprovarEmpresa: (id, usuarioInternoId) => request(`/api/empresas/${id}/reprovar`, {
    method: 'PATCH',
    body: JSON.stringify({ usuarioInternoId })
  })
}