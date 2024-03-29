# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/engine/reference/builder/

ARG PYTHON_VERSION=3.9
FROM python:${PYTHON_VERSION}-slim as base

RUN apt-get update
RUN apt-get install -y gdal-bin libgdal-dev g++

# Prevents Python from writing pyc files.
ENV PYTHONDONTWRITEBYTECODE=1

# Keeps Python from buffering stdout and stderr to avoid situations where
# the application crashes without emitting any logs due to buffering.
ENV PYTHONUNBUFFERED=1
ENV FLASK_RUN_HOST=0.0.0.0
ENV FLASK_RUN_PORT=5000

WORKDIR /app

# Create a non-privileged user that the app will run under.
# See https://docs.docker.com/develop/develop-images/dockerfile_best-practices/#user
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.cache/pip to speed up subsequent builds.
# Leverage a bind mount to requirements.txt to avoid having to copy them into
# into this layer.
RUN --mount=type=cache,target=/root/.cache/pip \
    --mount=type=bind,source=requirements.txt,target=requirements.txt \
    python -m pip install -r requirements.txt

# Commenting some of the OTEL stuff, as some parts are not 100% stable yet
#RUN pip install opentelemetry-distro
#RUN pip install opentelemetry-instrumentation-flask
#RUN pip install opentelemetry-exporter-otlp
#RUN pip install flask
#RUN pip install requests

#RUN opentelemetry-bootstrap -a install

#ENV OTEL_PYTHON_LOGGING_AUTO_INSTRUMENTATION_ENABLED=true

# Switch to the non-privileged user to run the application.
USER appuser

# Copy the source code into the container.
COPY . .

# Expose the port that the application listens on.
EXPOSE 5000

# Run the application.
CMD python3 -m flask run --host=0.0.0.0
#CMD opentelemetry-instrument \
#    --traces_exporter otlp \
#    --metrics_exporter otlp \
#    --logs_exporter console \
#    --service_name petrol-reports-server \
#    --exporter_otlp_endpoint http://otel-collector:4317 \
#    flask run --host=0.0.0.0