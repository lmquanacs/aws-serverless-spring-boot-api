resource "aws_dynamodb_table" "order-dynamodb-table" {
  name           = "order"
  billing_mode   = "PAY_PER_REQUEST"
  hash_key       = "order_uid"
  range_key      = "created_date"

  attribute {
    name = "order_uid"
    type = "S"
  }

  attribute {
    name = "created_date"
    type = "S"
  }

  attribute {
    name = "customer_name"
    type = "S"
  }

  attribute {
    name = "facebook_url"
    type = "S"
  }

  tags = {
    Name        = "Order table"
    Environment = "family"
  }
}