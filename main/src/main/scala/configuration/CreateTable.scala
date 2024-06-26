package configuration

import configuration.TableStructure.*
import zio.dynamodb.AttributeDefinition.*
import zio.dynamodb.DynamoDBQuery.{createTable, deleteTable}
import zio.dynamodb.*

object CreateTable {
  val TableName = "mappings"

  val deleteTableQuery = deleteTable(TableName)

  val createTableExample: DynamoDBQuery[Any, Unit] =
    createTable(
      tableName = TableName,
      keySchema = KeySchema(hashKey = "pk", sortKey = "sk"),
      BillingMode.provisioned(readCapacityUnit = 100, writeCapacityUnit = 100)
    )(
      attrDefnString(PK),
      attrDefnString(SK),
      attrDefnString("gsi_pk1"),
      attrDefnString("gsi_sk1"),
      attrDefnString(GSI_PK2), //the types have to be known at compile time
      attrDefnString(GSI_SK2)
    )
      .gsi(
        "gsi1",
        KeySchema(hashKey = "gsi_pk1", sortKey = "gsi_sk1"),
        ProjectionType.All,
        readCapacityUnit = 100,
        writeCapacityUnit = 100
      )
      .gsi(
        GSI_INDEX_NAME2,
        KeySchema(hashKey = GSI_PK2, sortKey = GSI_SK2),
        ProjectionType.All,
        readCapacityUnit = 100,
        writeCapacityUnit = 100
      )


}
