#EMS

## Getting started

To make it easy for you to get started with GitHub, here's a list of recommended next steps.

## Add your files

Clone the repo:

```
git clone https://github.com/BAndrei123/Energy-Management-System.git

```

## Needed tools

Download and install docker desktop : https://www.docker.com/products/docker-desktop/

## Run

Go to users-spring directory, open cmd and run the command:

```
docker-compose up

```

Exact the same steps as above for devices-spring

Go to project-front end directory, open cmd and run the commands:

```

docker build -t react-app .

docker run -p 3000:80 react-app

```

Start device simulator

Go to Provider project directory, and run the following python command

```
python provider.py <device_id>
```

## Have fun
