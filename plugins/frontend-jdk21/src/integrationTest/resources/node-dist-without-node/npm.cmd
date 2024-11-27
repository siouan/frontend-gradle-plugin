@ECHO off
ECHO %~dp0 %*
@IF EXIST "%~dp0\node.exe" (
    "%~dp0\node.exe" %*
) ELSE (
    node %*
)
