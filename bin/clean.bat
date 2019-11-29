rem /* ----- Cloud Service ----- */

cd ..
cd ctFire-config
call mvn clean

cd ..
cd ctFire-eureka
call mvn clean

cd ..
cd ctFire-zipkin
call mvn clean

cd ..
cd ctFire-admin
call mvn clean

cd ..
cd ctFire-zuul
call mvn clean

rem /* ----- Common Libs ----- */

cd ..
cd ctFire-dependencies
call mvn clean

cd ..
cd ctFire-common
call mvn clean

cd ..
cd ctFire-common-domain
call mvn clean

cd ..
cd ctFire-common-service
call mvn clean

cd ..
cd ctFire-common-web
call mvn clean

rem /* ----- Service Provider ----- */

cd ..
cd ctFire-service-redis
call mvn clean

cd ..
cd ctFire-service-sso
call mvn clean

cd ..
cd ctFire-service-user
call mvn clean



rem /* ----- Service Consumer ----- */

cd ..
cd ctFire-web-user
call mvn clean


cd ..