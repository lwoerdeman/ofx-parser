terraform {
  backend "remote" {
    organization = "WardemonApps"

    workspaces {
      name = "myfi-01"
    }
  }

  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

provider "google" {
  version = "3.5.0"

  project = "myfi-01"
  region  = "us-central1"
  zone    = "us-central1-c"
}

resource "google_cloud_run_service" "default" {
  name     = "myfi-srv"
  location = "us-central1"

  template {
    spec {
      containers {
        image = "gcr.io/myfi-01/app-server"
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service_iam_policy" "noauth" {
  location = google_cloud_run_service.default.location
  project  = google_cloud_run_service.default.project
  service  = google_cloud_run_service.default.name

  policy_data = data.google_iam_policy.noauth.policy_data
}
