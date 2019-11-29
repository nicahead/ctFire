rem /* ----- Cloud Service ----- */

cd ..
cd ctFire-config
call mvn clean install

cd ..
cd ctFire-eureka
call mvn clean install

cd ..
cd ctFire-zipkin
call mvn clean install

cd ..
cd ctFire-admin
call mvn clean install

cd ..
cd ctFire-zuul
call mvn clean install

rem /* ----- Common Libs ----- */

cd ..
cd ctFire-dependencies
call mvn clean install

cd ..
cd ctFire-common
call mvn clean install

cd ..
cd ctFire-common-domain
call mvn clean install

cd ..
cd ctFire-common-service
call mvn clean install

cd ..
cd ctFire-common-web
call mvn clean install

rem /* ----- Service Provider ----- */

cd ..
cd ctFire-service-redis
call mvn clean install

cd ..
cd ctFire-service-sso
call mvn clean install

cd ..
cd ctFire-service-user
call mvn clean install

cd ..
cd ctFire-service-device
call mvn clean install

cd ..
cd ctFire-service-data
call mvn clean install

rem /* ----- Service Consumer ----- */

cd ..
cd ctFire-web-user
call mvn clean install

cd ..
cd ctFire-web-device
call mvn clean install

cd ..
cd ctFire-web-data
call mvn clean install

cd ..
cd ctFire-web-backend
call mvn clean install

cd ..