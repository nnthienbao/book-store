thrift --gen java Book.thrift
thrift --gen java User.thrift
rsync -a gen-java/com ../java
rm -rf gen-java
