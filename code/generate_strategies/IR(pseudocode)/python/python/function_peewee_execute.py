from peewee import SqliteDatabase, Model, CharField

class PeeweeExecutor:
    def execute_query(self, db_path: str, query: str) -> str:
        database = SqliteDatabase(db_path)
        class BaseModel(Model):
            class Meta:
                database = SqliteDatabase(db_path)

        class TestModel(BaseModel):
            name = CharField()

        database.connect()
        database.create_tables([TestModel])

        affected_rows = database.execute_sql(query)
        database.close()

        return str(affected_rows.rowcount)
