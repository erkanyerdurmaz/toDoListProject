set -m

/entrypoint.sh couchbase-server &

sleep 15

curl -v -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=512 -d indexMemoryQuota=512

curl -v http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2cn1ql%2Cindex

curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username=Administrator -d password=password

curl -i -u Administrator:password -X POST http://127.0.0.1:8091/settings/indexes -d 'storageMode=memory_optimized'

curl -v -u Administrator:password -X POST http://127.0.0.1:8091/pools/default/buckets -d name=default -d bucketType=couchbase -d ramQuotaMB=128 -d authType=sasl -d saslPassword=default_PASSWORD

sleep 15

curl -v -u Administrator:password http://127.0.0.1:8093/query/service -d "statement=CREATE PRIMARY INDEX ON default"

curl -v -u Administrator:password http://127.0.0.1:8093/query/service -d "statement=CREATE INDEX idx_userId ON default(userId)"

fg 1