FROM gradle:7-jdk17-alpine
COPY . /src/sem_work_tjv
WORKDIR /src/sem_work_tjv

RUN gradle --no-daemon build
CMD gradle --no-daemon bootRun
