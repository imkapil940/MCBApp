param(
    [string]$Root = (Get-Location).Path
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

Set-Location -Path $Root

# Directories to exclude from processing
$excludeDirs = @('AllureReport','allure-results','test-output','target','logs','.git','.idea')

# Build a regex that matches any excluded directory anywhere in the path
$sep = [IO.Path]::DirectorySeparatorChar
$escapedParts = @()
foreach ($d in $excludeDirs) {
    $escapedParts += [regex]::Escape("$sep$d$sep")
}
$excludeRegex = [string]::Join('|', $escapedParts)

# File extensions to include for content replacement
$includeExts = '*.java','*.xml','*.properties','*.md','*.sh','*.bat','*.yml','*.yaml','*.txt'

Write-Host "Replacing 'Ixigo' -> 'Mcb' in content..."
$filesForContent = Get-ChildItem -Path .\* -Recurse -File -Include $includeExts |
    Where-Object { $_.FullName -notmatch $excludeRegex }

foreach ($file in $filesForContent) {
    $content = Get-Content -Raw -LiteralPath $file.FullName
    $updated = $content -replace 'Ixigo','Mcb'
    if ($updated -ne $content) {
        Set-Content -LiteralPath $file.FullName -Value $updated -Encoding UTF8
        Write-Host "Updated content:" $file.FullName
    }
}

Write-Host "Renaming files with 'Ixigo' in name..."
$filesToRename = Get-ChildItem -Path .\* -Recurse -File -Filter '*Ixigo*' |
    Where-Object { $_.FullName -notmatch $excludeRegex }

foreach ($file in $filesToRename) {
    $newName = $file.Name -replace 'Ixigo','Mcb'
    if ($newName -ne $file.Name) {
        Rename-Item -LiteralPath $file.FullName -NewName $newName
        Write-Host "Renamed file:" $file.FullName "->" (Join-Path $file.DirectoryName $newName)
    }
}

Write-Host "Done."


