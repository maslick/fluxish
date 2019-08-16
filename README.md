# r2dbc performance test


```bash
$ echo "GET http://localhost:8080/feeds" | vegeta attack -rate=300 -duration=15s | tee results.bin | vegeta report
Requests      [total, rate]            4500, 300.07
Duration      [total, attack, wait]    15.002030043s, 14.996579s, 5.451043ms
Latencies     [mean, 50, 95, 99, max]  6.022239ms, 6.036946ms, 6.854407ms, 8.446394ms, 12.119311ms
Bytes In      [total, mean]            3906000, 868.00
Bytes Out     [total, mean]            0, 0.00
Success       [ratio]                  100.00%
Status Codes  [code:count]             200:4500

$ cat results.bin | vegeta report -type="hist[0,5ms,10ms,200ms,300ms,1s,5s]"
Bucket           #     %       Histogram
[0s,     5ms]    36    0.80%
[5ms,    10ms]   4449  98.87%  ##########################################################################
[10ms,   200ms]  15    0.33%
[200ms,  300ms]  0     0.00%
[300ms,  1s]     0     0.00%
[1s,     5s]     0     0.00%
```


```bash
$ echo "POST http://localhost:8080/post" | vegeta attack -body payload.json -rate=100 -duration=5s | tee results.bin | vegeta report
  Requests      [total, rate]            500, 100.16
  Duration      [total, attack, wait]    4.998697352s, 4.99187s, 6.827352ms
  Latencies     [mean, 50, 95, 99, max]  8.817106ms, 7.730904ms, 12.161913ms, 38.713621ms, 47.984526ms
  Bytes In      [total, mean]            25476, 50.95
  Bytes Out     [total, mean]            2000, 4.00
  Success       [ratio]                  100.00%
  Status Codes  [code:count]             200:500
  Error Set:
  
$ cat results.bin | vegeta report -type="hist[0,5ms,10ms,20ms,50ms,100ms]"
Bucket           #    %       Histogram
[0s,     5ms]    0    0.00%
[5ms,    10ms]   453  90.60%  ###################################################################
[10ms,   20ms]   30   6.00%   ####
[20ms,   50ms]   17   3.40%   ##
[50ms,   100ms]  0    0.00%
[100ms,  +Inf]   0    0.00%
```