from peewee import SqliteDatabase, Model, CharField, IntegerField

class PeeweeInsert:
    def insert_record(self, db_path: str, name: str, age: int) -> str:
        database = SqliteDatabase(db_path)

        class TestModel(Model):
            name = CharField()
            age = IntegerField()

            class Meta:
                database = SqliteDatabase(db_path)

        database.connect()
        database.create_tables([TestModel])

        try:
            query = TestModel.insert(name=name, age=age)
            record_id = query.execute()
            return str(record_id)
        except Exception:
            return "Insert Failed"
        finally:
            database.close()
