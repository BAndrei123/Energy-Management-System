import pandas as pd
import time
import argparse
import pika
import json
from datetime import datetime

# Argument parser for device id
parser = argparse.ArgumentParser()
parser.add_argument("id")  # Device ID passed as argument
args = parser.parse_args()

print(f"Hello, {args.id}!")
# Initialize variables
df = pd.read_csv("sensor.csv")

interval = 6  # Rows per 10-minute interval
last_consumption = 0  # Starting with 0
index = 0  # Start from the first row

# RabbitMQ connection
connection = pika.BlockingConnection(pika.ConnectionParameters('localhost', 5672))
channel = connection.channel()

# Declare a queue (make sure it's durable)
queue_name = "consumption"
channel.queue_declare(queue=queue_name, durable=
True)

# Loop to process the consumption every 10 minutes
while index < len(df):
    # Get the current value at the index
    current_value = df.iloc[index]["consumption"]

    # Calculate consumption
    consumption = current_value - last_consumption

    # Prepare the message in JSON format
    message = {
        "timestamp": int(datetime.now().timestamp()),  # Current timestamp in ISO format
        "deviceId": str(args.id),  # Device ID from argument
        "consumption": consumption
    }

    # Send the consumption data to RabbitMQ
    channel.basic_publish(
        exchange='',  # Default exchange
        routing_key=queue_name,  # Queue name
        body=json.dumps(message),  # Convert Python dict to JSON string
        properties=pika.BasicProperties(
            delivery_mode=2,  # Make the message persistent
        )
    )

    print(f" [x] Sent {json.dumps(message)} to RabbitMQ")

    # Update the last consumption
    last_consumption = current_value

    # Move to the next interval
    index += interval
    print("Index: ", index)

    # Wait for 10 minutes before the next execution
    time.sleep(10)  # Sleep for 10 minutes (600 seconds)

# Close the connection
connection.close()
