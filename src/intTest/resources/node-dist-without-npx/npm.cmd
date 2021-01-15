@ECHO off
ECHO %~f0: PATH=%PATH%
SET "NODE_EXE=%~dp0node"
ECHO %~f0 %*
"%NODE_EXE%" %*
