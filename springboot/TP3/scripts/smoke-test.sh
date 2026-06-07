#!/usr/bin/env bash

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"

log() {
  printf '[smoke] %s\n' "$1"
}

fail() {
  printf '[smoke][erro] %s\n' "$1" >&2
  exit 1
}

request() {
  local method="$1"
  local path="$2"
  local body="${3:-}"

  if [[ -n "$body" ]]; then
    curl --silent --show-error --fail \
      -X "$method" \
      -H "Content-Type: application/json" \
      -d "$body" \
      "$BASE_URL$path"
  else
    curl --silent --show-error --fail \
      -X "$method" \
      "$BASE_URL$path"
  fi
}

wait_for_health() {
  local attempts=30
  local response=""

  for ((i = 1; i <= attempts; i++)); do
    response="$(curl --silent "$BASE_URL/actuator/health" || true)"
    if [[ "$response" == *'"status":"UP"'* ]]; then
      log "Actuator health respondeu UP."
      return 0
    fi
    sleep 2
  done

  fail "Actuator health não ficou UP. Última resposta: ${response:-sem resposta}"
}

assert_contains() {
  local value="$1"
  local expected="$2"
  local context="$3"

  if [[ "$value" != *"$expected"* ]]; then
    fail "$context não contém '$expected'. Resposta: $value"
  fi
}

log "Aguardando aplicação ficar saudável em $BASE_URL..."
wait_for_health

log "Validando listagem de alunos ativos..."
ativos_response="$(request GET /api/alunos/ativos)"
assert_contains "$ativos_response" "Ana Silva" "Listagem de alunos ativos"

log "Validando ranking de alunos..."
ranking_response="$(request GET /api/alunos/ranking)"
assert_contains "$ranking_response" "Ana Silva" "Ranking de alunos"

log "Cadastrando novo aluno..."
novo_aluno_response="$(request POST /api/alunos '{"nome":"Julia Teste","email":"julia.teste@academia.com","dataNascimento":"2000-01-15","ativo":true,"planoId":1}')"
assert_contains "$novo_aluno_response" "Julia Teste" "Cadastro de aluno"

log "Cadastrando novo treino..."
novo_treino_response="$(request POST /api/treinos '{"nomeTreino":"Treino Smoke","focoPrincipal":"Mobilidade","instrutorId":1}')"
assert_contains "$novo_treino_response" "Treino Smoke" "Cadastro de treino"

log "Cadastrando avaliação física..."
avaliacao_response="$(request POST /api/avaliacoes-fisicas '{"alunoId":1,"peso":72.4,"altura":1.73,"percentualGordura":19.1,"anotacoesMedicas":"Avaliação de smoke test"}')"
assert_contains "$avaliacao_response" '"alunoId":1' "Cadastro de avaliação física"

log "Consultando avaliações físicas do aluno 1..."
avaliacoes_aluno_response="$(request GET /api/avaliacoes-fisicas/aluno/1)"
assert_contains "$avaliacoes_aluno_response" "Avaliação de smoke test" "Listagem de avaliações físicas"

log "Smoke test concluído com sucesso."
