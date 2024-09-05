# Запуск приложения
Все действия описаны для операционной сисемы MacOS и Docker
## Предварительные требования
### Запустить БД
- установить PostgreSQL `brew install postgresql`
- ` -it \
  -v postgresml_data:/var/lib/postgresql \
  -p 5433:5432 \
  -p 8000:8000 \
  ghcr.io/postgresml/postgresml:2.9.3 \
  sudo -u postgresml psql -d postgresml`
- зайти из консоли в бд `psql -h localhost -p 5432 -U yander -d zorinovAiBot`
- ввести пароль `yander`

