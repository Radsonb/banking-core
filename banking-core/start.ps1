Set-Location $PSScriptRoot

$portInUse = [System.Net.NetworkInformation.IPGlobalProperties]::GetIPGlobalProperties().GetActiveTcpListeners() |
    Where-Object { $_.Port -eq 8080 }
if ($portInUse) {
    Write-Host 'A porta 8080 ja esta em uso. Se o projeto estiver rodando em outro terminal, encerre com Ctrl+C antes de iniciar novamente.' -ForegroundColor Yellow
    exit 1
}

docker compose up -d --wait postgres keycloak
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
exit $LASTEXITCODE
