#!/usr/bin/env bash

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"

log() {
  printf '[smoke-gets] %s\n' "$1"
}

fail() {
  printf '[smoke-gets][erro] %s\n' "$1" >&2
  exit 1
}

request() {
  local path="$1"
  curl --silent --show-error --fail "$BASE_URL$path"
}

print_json() {
  local title="$1"
  local payload="$2"

  printf '\n[%s]\n' "$title"
  printf '%s\n' "$payload" | jq
  printf '\n'
}

assert_contains() {
  local value="$1"
  local expected="$2"
  local context="$3"

  if [[ "$value" != *"$expected"* ]]; then
    fail "$context não contém '$expected'. Resposta: $value"
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

log "Aguardando aplicação ficar saudável em $BASE_URL..."
wait_for_health

log "Validando GET /actuator/health..."
health_response="$(request /actuator/health)"
assert_contains "$health_response" '"status":"UP"' "Actuator health"
print_json "GET /actuator/health" "$health_response"

log "Validando GET /api/alunos/ativos..."
ativos_response="$(request /api/alunos/ativos)"
assert_contains "$ativos_response" "Ana Silva" "Listagem de alunos ativos"
print_json "GET /api/alunos/ativos" "$ativos_response"

log "Validando GET /api/alunos/ranking..."
ranking_response="$(request /api/alunos/ranking)"
assert_contains "$ranking_response" "Ana Silva" "Ranking de alunos"
print_json "GET /api/alunos/ranking" "$ranking_response"

log "Validando GET /api/avaliacoes-fisicas/aluno/1..."
avaliacoes_response="$(request /api/avaliacoes-fisicas/aluno/1)"
if [[ "$avaliacoes_response" != "[]" ]]; then
  assert_contains "$avaliacoes_response" '"alunoId":1' "Listagem de avaliações físicas"
fi
print_json "GET /api/avaliacoes-fisicas/aluno/1" "$avaliacoes_response"

log "Smoke test de GETs concluído com sucesso."
