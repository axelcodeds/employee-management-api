# CI/CD Setup

This document describes the Continuous Integration and Continuous Deployment (CI/CD) configuration for the Employee Management System API.

## GitHub Actions Workflows

### 1. Build and Test Workflow (build.yml)

**Trigger**: Push to `main` or `development` branches, Pull Requests

**Steps**:
- Checkout code
- Setup JDK 21
- Build Maven project
- Run unit tests
- Upload test results as artifacts
- Generate test report summary

**Files Modified**: None (new workflow)

### 2. Docker Build and Push Workflow (docker.yml)

**Trigger**: Push to `main` branch, Tags matching `v*`, Pull Requests

**Steps**:
- Checkout code
- Setup Docker Buildx
- Login to Docker Hub (for main branch only)
- Extract metadata from tags/branches
- Build and push Docker image with caching

**Configuration Required**:
- `DOCKER_USERNAME`: Docker Hub username
- `DOCKER_PASSWORD`: Docker Hub access token/password

**Image Tags**:
- Branch-based tags (e.g., `main`, `development`)
- Semantic version tags (e.g., `v1.0.0`, `1.0`)
- Commit SHA tags
- Latest tag for default branch

### 3. Code Quality Workflow (quality.yml)

**Trigger**: Push to `main` or `development` branches, Pull Requests

**Steps**:
- Checkout code (with full history for SonarQube)
- Setup JDK 21
- Run SonarQube analysis (optional, continues on error)
- Check build compilation

**Configuration Required** (Optional):
- `SONAR_HOST_URL`: SonarQube server URL
- `SONAR_LOGIN`: SonarQube authentication token

## Setup Instructions

### Prerequisites
- GitHub repository
- Docker Hub account (for Docker workflow)
- SonarQube instance (optional, for code quality)

### 1. Basic Setup (Build and Test)

No additional setup required. The build workflow will run automatically on push and pull requests.

### 2. Docker Setup

To enable Docker image building and pushing:

1. Navigate to your GitHub repository settings
2. Go to **Secrets and variables** → **Actions**
3. Add the following secrets:
   - `DOCKER_USERNAME`: Your Docker Hub username
   - `DOCKER_PASSWORD`: Your Docker Hub access token

4. Update your `docker.yml` workflow if needed:
   - Change `docker_username/ems` to your desired image name
   - Adjust tag patterns as needed

### 3. SonarQube Setup (Optional)

To enable code quality analysis:

1. Setup a SonarQube instance or use SonarCloud
2. Add secrets to your GitHub repository:
   - `SONAR_HOST_URL`: URL of your SonarQube instance
   - `SONAR_LOGIN`: SonarQube authentication token

3. Update `quality.yml` if using a different SonarQube key

## Monitoring and Viewing Results

### Test Results
- View test artifacts in the **Actions** tab after each workflow run
- Download test reports from the artifacts section

### Docker Images
- Built images are pushed to Docker Hub with tags
- Pull images using: `docker pull dockerhub-username/ems:tag`

### Code Quality
- View SonarQube analysis in your SonarQube dashboard
- Failed quality gates can be configured to block merges

## Workflow Status Badges

Add these badges to your README.md:

```markdown
[![Build and Test](https://github.com/your-username/employee-management-api/actions/workflows/build.yml/badge.svg)](https://github.com/your-username/employee-management-api/actions/workflows/build.yml)
[![Docker Build](https://github.com/your-username/employee-management-api/actions/workflows/docker.yml/badge.svg)](https://github.com/your-username/employee-management-api/actions/workflows/docker.yml)
[![Code Quality](https://github.com/your-username/employee-management-api/actions/workflows/quality.yml/badge.svg)](https://github.com/your-username/employee-management-api/actions/workflows/quality.yml)
```

## Troubleshooting

### Docker Build Fails
- Ensure `Dockerfile` is present in the repository root
- Check Docker Hub credentials in secrets
- Verify image name doesn't contain uppercase letters

### Tests Fail in CI
- Check Maven version compatibility (currently using Maven wrapper)
- Verify database configuration for integration tests
- Review test logs in the Actions tab

### SonarQube Integration Issues
- Verify SonarQube server URL is correct
- Check authentication token validity
- Ensure project key matches your setup

## Best Practices

1. **Branch Protection**: Require CI/CD workflows to pass before merging
2. **Semantic Versioning**: Use `v*` tags for releases (e.g., `v1.0.0`)
3. **Test Coverage**: Maintain high test coverage (currently at 46 unit tests)
4. **Docker Image Cleanup**: Regularly prune old images from Docker Hub
5. **Secret Management**: Never commit secrets, use GitHub secrets exclusively

