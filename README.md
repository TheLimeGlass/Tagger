# Ticker
A Tps logistics Skript addon.

Keep in mind that this is alot more accurate than Bukkit's Tps methods. Ticker's average Tps is similar to Bukkit's Tps, but there is also an expression to get from Bukkit.

Here is a list of syntax:
```
Syntax:
  Events:
    AverageTpsChangeEvent:
      enabled: true
      patterns:
      - '[Ticker] average tps change [to %-number%]'
      eventvalues:
      - Number
      - Number
      - Number
    TpsChangeEvent:
      enabled: true
      patterns:
      - '[Ticker] tps change [to %-number%]'
      eventvalues:
      - Number
      - Number
      - Number
  Expressions:
    ExprEventPastTps:
      enabled: true
      description: Returns the server's past tps in the tps change event.
      syntax:
      - '[the] [server[['']s]] (past|previous) tps'
    ExprTpsString:
      enabled: true
      description: Returns the server's tps with colour.
      syntax:
      - '[the] [server[['']s]] tps string'
    ExprAverageTps:
      enabled: true
      description: Returns the server's average tps.
      syntax:
      - '[the] [server[['']s]] average tps'
    ExprBukkitTpsString:
      enabled: true
      description: Returns the server's tps from Bukkit.
      syntax:
      - '[the] bukkit[['']s] tps [string]'
    ExprEventPastAverageTps:
      enabled: true
      description: Returns the server's past average tps in the average tps change
        event.
      syntax:
      - '[the] [server[['']s]] (past|previous) average tps'
    ExprTps:
      enabled: true
      description: Returns the server's tps.
      syntax:
      - '[the] [server[['']s]] tps'
    ExprLastTps:
      enabled: true
      description: Returns the server's last tps from 20 ticks ago.
      syntax:
      - '[the] [server[['']s]] last tps'
    ExprAverageTpsString:
      enabled: true
      description: Returns the server's average tps with colour.
      syntax:
      - '[the] [server[['']s]] average tps string'
  PropertyExpressions:
    ExprTimeTps:
      enabled: true
      description: Returns the server's tps at a time.
      syntax:
      - '[(all [[of] the]|the)] [the] [server[['']s]] tps[s] (of|from) [date[s]] %date%'
      - '%date%[''s] [date[s]] [the] [server[['']s]] tps[s]'
```