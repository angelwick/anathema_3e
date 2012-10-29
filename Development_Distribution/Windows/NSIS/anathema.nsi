# Auto-generated by EclipseNSIS Script Wizard
# Aug 7, 2010 12:19:00 PM
#
# Revised by hand afterwards

# Requires AccessControl plugin: http://nsis.sourceforge.net/AccessControl_plug-in

Name Anathema

SetCompressor lzma

# General Symbol Definitions
!define REGKEY "SOFTWARE\$(^Name)"
!define COMPANY "Anathema Team"
!define URL http://anathema.github.com

# MultiUser Symbol Definitions
!define MULTIUSER_EXECUTIONLEVEL Power
!define MULTIUSER_MUI
!define MULTIUSER_INSTALLMODE_DEFAULT_REGISTRY_KEY "${REGKEY}"
!define MULTIUSER_INSTALLMODE_DEFAULT_REGISTRY_VALUENAME MultiUserInstallMode
!define MULTIUSER_INSTALLMODE_COMMANDLINE
!define MULTIUSER_INSTALLMODE_INSTDIR Anathema
!define MULTIUSER_INSTALLMODE_INSTDIR_REGISTRY_KEY "${REGKEY}"
!define MULTIUSER_INSTALLMODE_INSTDIR_REGISTRY_VALUE "Path"

# MUI Symbol Definitions
!define MUI_ICON sungearInstall.ico
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT HKLM
!define MUI_STARTMENUPAGE_REGISTRY_KEY ${REGKEY}
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME StartMenuGroup
!define MUI_STARTMENUPAGE_DEFAULTFOLDER Anathema
!define MUI_FINISHPAGE_LINK "Visit our website"
!define MUI_FINISHPAGE_LINK_LOCATION "${URL}"
!define MUI_FINISHPAGE_RUN_TEXT "Launch Anathema"
!define MUI_FINISHPAGE_RUN "$INSTDIR\Anathema.exe"
!define MUI_FINISHPAGE_RUN_NOTCHECKED
!define MUI_FINISHPAGE_SHOWREADME_TEXT "View the release notes"
!define MUI_FINISHPAGE_SHOWREADME 'https://github.com/anathema/anathema/blob/v${RELEASE_VERSION}/Development_Documentation/Distribution/English/versions.md'
!define MUI_FINISHPAGE_SHOWREADME_CHECKED
!define MUI_UNICON sungearInstall.ico
!define MUI_UNFINISHPAGE_NOAUTOCLOSE

!define MUI_WELCOMEFINISHPAGE_BITMAP Anathema_Install.bmp
!define MUI_UNWELCOMEFINISHPAGE_BITMAP Anathema_Install.bmp
!define MUI_HEADERIMAGE_RIGHT Anathema_Install_small.bmp

# Included files
!include MultiUser.nsh
!include Sections.nsh
!include MUI2.nsh

# Variables
Var StartMenuGroup

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE ..\..\..\Development_Documentation\Distribution\English\license.txt
!insertmacro MULTIUSER_PAGE_INSTALLMODE
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuGroup
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

# Installer languages
!insertmacro MUI_LANGUAGE English
!insertmacro MUI_LANGUAGE Spanish
!insertmacro MUI_LANGUAGE Italian

# Installer attributes
OutFile "${RELEASE_DIR}\${RELEASE_FILE}"
InstallDir Anathema
CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion ${VERSION}
VIAddVersionKey /LANG=${LANG_ENGLISH} ProductName Anathema
VIAddVersionKey /LANG=${LANG_ENGLISH} ProductVersion "${RELEASE_VERSION}"
VIAddVersionKey /LANG=${LANG_ENGLISH} CompanyName "${COMPANY}"
VIAddVersionKey /LANG=${LANG_ENGLISH} CompanyWebsite "${URL}"
VIAddVersionKey /LANG=${LANG_ENGLISH} FileVersion "${RELEASE_VERSION}"
VIAddVersionKey /LANG=${LANG_ENGLISH} FileDescription ""
VIAddVersionKey /LANG=${LANG_ENGLISH} LegalCopyright ""
InstallDirRegKey HKLM "${REGKEY}" Path
ShowUninstDetails show

# Installer sections
Section -Anathema SEC0000
    RMDir /r $INSTDIR\lib
    RMDir /r $INSTDIR\plugins
    SetOutPath $INSTDIR\lib
    SetOverwrite on
    File /r ..\..\..\build\dependencies\*
    File /r ..\..\..\build\plugins\*
    SetOutPath $INSTDIR\jre
    File /r ..\..\..\${JRE_PATH}\*
    SetOutPath $INSTDIR
    File ..\..\..\Anathema\build\libs\Anathema.jar
    File ..\..\..\build\launcher\anathema.exe
    File ..\..\..\Development_Documentation\Distribution\English\license.txt
    File /oname=release_notes.txt ..\..\..\Development_Documentation\Distribution\English\versions.md
    WriteRegStr HKLM "${REGKEY}\Components" Anathema 1
SectionEnd

Section -post SEC0001
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    SetOutPath $INSTDIR
    CreateDirectory $INSTDIR\repository
    AccessControl::GrantOnFile "$INSTDIR\repository" "(S-1-5-32-545)" "FullAccess"
    #Exec 'cacls.exe "$INSTDIR\repository" /t /e /p USERS:F' ## Alternate method to using AccessControl Plugin
    WriteUninstaller $INSTDIR\uninstall.exe
    !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    CreateDirectory $SMPROGRAMS\$StartMenuGroup
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^Name).lnk" $INSTDIR\anathema.exe
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^UninstallLink).lnk" $INSTDIR\uninstall.exe
    !insertmacro MUI_STARTMENU_WRITE_END
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${RELEASE_VERSION}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" Publisher "${COMPANY}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" URLInfoAbout "${URL}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 HKLM "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    GoTo done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o -un.Anathema UNSEC0000
    RmDir /r /REBOOTOK $INSTDIR\lib
    RmDir /r /REBOOTOK $INSTDIR\jre
    Delete /REBOOTOK $INSTDIR\anathema.jar
    Delete /REBOOTOK $INSTDIR\anathema.exe
    Delete /REBOOTOK $INSTDIR\license.txt
    Delete /REBOOTOK $INSTDIR\release_notes.txt
    DeleteRegValue HKLM "${REGKEY}\Components" Anathema
SectionEnd

Section -un.post UNSEC0001
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^UninstallLink).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^Name).lnk"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue HKLM "${REGKEY}" StartMenuGroup
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /IfEmpty HKLM "${REGKEY}\Components"
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"
    RmDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RmDir /REBOOTOK $INSTDIR\repository
    RmDir /REBOOTOK $INSTDIR
    Push $R0
    StrCpy $R0 $StartMenuGroup 1
    StrCmp $R0 ">" no_smgroup
no_smgroup:
    Pop $R0
SectionEnd

# Installer functions
Function .onInit
    InitPluginsDir
    !insertmacro MUI_LANGDLL_DISPLAY
    !insertmacro MULTIUSER_INIT
FunctionEnd

# Uninstaller functions
Function un.onInit
    !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuGroup
    !insertmacro MULTIUSER_UNINIT
    !insertmacro SELECT_UNSECTION Anathema ${UNSEC0000}
FunctionEnd

# Section Descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_FUNCTION_DESCRIPTION_END

# Installer Language Strings

LangString ^UninstallLink ${LANG_ENGLISH} "Uninstall $(^Name)"
LangString ^UninstallLink ${LANG_SPANISH} "Desinstalar $(^Name)"
LangString ^UninstallLink ${LANG_ITALIAN} "Disinstallare $(^Name)"