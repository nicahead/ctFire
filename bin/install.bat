rem /* ----- Cloud Service ----- */

cd ..
cd ctFire-config
call mvn install

cd ..
cd ctFire-eureka
call mvn install

cd ..
cd ctFire-zipkin
call mvn install

cd ..
cd ctFire-admin
call mvn install

cd ..
cd ctFire-zuul
call mvn install

rem /* ----- Common Libs ----- */

cd ..
cd ctFire-dependencies
call mvn install

cd ..
cd ctFire-common
call mvn install

cd ..
cd ctFire-common-domain
call mvn install

cd ..
cd ctFire-common-service
call mvn install

cd ..
cd ctFire-common-web
call mvn install

rem /* ----- Service Provider ----- */

cd ..
cd ctFire-service-redis
call mvn install

cd ..
cd ctFire-service-sso
call mvn install

cd ..
cd ctFire-service-user
call mvn install



rem /* ----- Service Consumer ----- */

cd ..
cd ctFire-web-user
call mvn install


cd ..