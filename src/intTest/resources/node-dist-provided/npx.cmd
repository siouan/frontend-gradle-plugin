@ECHO off
ECHO %~0 %*
@IF EXIST "%~dp0\node.exe" (
    "%~dp0\node.exe" %*
) ELSE (
    node %*
)
