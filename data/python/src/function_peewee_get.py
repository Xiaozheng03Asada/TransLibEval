from peewee import SqliteDatabase, Model, CharField, IntegerField

class PeeweeExecutor:
    def get_name_by_id(self, db_path: str, record_id: int) -> str:
        db = SqliteDatabase(db_path)

        class BaseModel(Model):
            class Meta:
                database = db

        class TestModel(BaseModel):
            id = IntegerField(primary_key=True)
            name = CharField()

        db.connect()
        db.create_tables([TestModel], safe=True)

        # 插入测试数据
        if not TestModel.select().where(TestModel.id == 1).exists():
            TestModel.create(id=1, name="Alice")
            TestModel.create(id=2, name="Bob")

        # 查询指定 ID 的 name 值
        try:
            result = TestModel.get(TestModel.id == record_id)
            return result.name
        except TestModel.DoesNotExist:
            return "Not Found"
        finally:
            db.close()
