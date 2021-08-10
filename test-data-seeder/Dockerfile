FROM mongo

COPY cargo-init.json /cargo-init.json
COPY flight-init.json /flight-init.json

CMD mongoimport --host mongo-db --db test --collection cargo --type json --file /cargo-init.json --jsonArray ; mongoimport --host mongo-db --db test --collection flight --type json --file /flight-init.json --jsonArray

