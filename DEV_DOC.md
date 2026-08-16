
```bash
Spring demarre
         │
         ▼
    il parcourt tes packages et cherche les annotations
         │
         ├── @Component  JwtUtils            → cree une instance
         ├── @Service    CustomUserDetail... → cree une instance
         ├── @RestController AuthController  → cree une instance
```bash
