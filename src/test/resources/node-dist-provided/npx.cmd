@ECHO off
ECHO %~f0: PATH=%PATH%
SET "NPM_EXE=%~dp0npm"
ECHO %~f0 %*
"%NPM_EXE%" %*
