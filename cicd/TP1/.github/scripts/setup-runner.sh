#!/bin/bash
echo "=== Configuração do GitHub Actions Runner ==="

mkdir -p actions-runner && cd actions-runner
curl -o actions-runner-linux-x64-2.322.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.322.0/actions-runner-linux-x64-2.322.0.tar.gz
tar xzf ./actions-runner-linux-x64-2.322.0.tar.gz
rm actions-runner-linux-x64-2.322.0.tar.gz

# ./run.sh

echo "Runner configurado. Complete o registro manualmente no GitHub."
