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

        cursor = database.execute_sql(query)
        fetched_result = cursor.fetchone()
        database.close()

        return str(fetched_result[0]) if fetched_result else "0"

