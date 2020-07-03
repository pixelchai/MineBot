:: Deps: Java8 JDK (AdoptOpenJDK), python3, git
:: NB: might need to restart PC after installing Java8 JDK
pip3 install gym lxml numpy pillow
git clone "https://github.com/Microsoft/malmo.git"
cd malmo/Minecraft
set /p VER=<../VERSION
echo malmomod.version=%VER% > ./src/main/resources/version.properties