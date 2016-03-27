# Introduction #

Le projet est décomposé en plusieurs sous-projets (ou modules), ayant chacun une fonction particulière.
Le schéma ci-dessous présente donc ces différents modules ainsi que leurs dépendances entres eux. Les **scopes** sont également indiqués par une lettre sur la relation (R pour Runtime et C pour Compile).
Le module **App** est en fait un ensemble de sous-pojets, puisqu'il contient aussi bien l'[application Web](StructureWeb.md) que les _Web services_ et le _batch_.


# Schéma #

```
                  +--> Model <--+
                C/               \C
                /                 \
       C       |                   |
App -----> Service API          DAO API
  \            ^               ^   ^
   \           |          C   /    |
   R\         C|       +-----+     |C
     \         |      /            |
      \        |     /             |
       +-> Service Impl ------> DAO Impl
                           R
```