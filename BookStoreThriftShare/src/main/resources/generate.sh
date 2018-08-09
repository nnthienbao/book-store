thrift --gen java Book.thrift
rsync -a gen-java/com ../java
rm -rf gen-java
