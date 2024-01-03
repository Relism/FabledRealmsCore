
# FabledRealmsCore

You are on the main branch of the FabledRealmsCore project. Here, you will find useful information and documentation to get you started or help you through the development process.

**Branch Management**

I have introduced a new branch structure to streamline our development and release process. We now have two main branches:

- `dev` branch: Use this branch for development builds.
- `prod` branch: Use this branch for production releases.

**Dev Branch**

When working on a development build, push your changes from your integrated development environment (IDE) to the `dev` branch. This action will trigger the 'build-dev-jars.yml' workflow. This workflow automates the deployment of your changes to the development Minecraft server and reloads it for testing.

**Prod Branch**

Pushes to the `prod` branch **require authorization and team discussion**. Pushing to 'prod' triggers the 'build-prod-jars.yml' workflow, which prepares the production jar and sends it to the production server. Manual restarts will be scheduled by moderators to minimize disruptions for our future playerbase.

Additionally, it's worth noting that, apart from sending the jar to the production server, it also saves it as a [release](https://github.com/FabledRealms/FabledRealmsCore/releases) in the GitHub repository, along with its source code.

**Continuous Integration and Continuous Deployment (CI/CD)**

Our CI/CD configuration is visualized below:

![CI/CD Diagram](https://raw.githubusercontent.com/FabledRealms/FabledRealmsCore/main/cicd_diagram_dark.svg)

This diagram illustrates our CI/CD process. The FabledRealms API handles deployments, reloading for testing (in the 'dev' branch), and scheduling manual restarts for the 'prod' branch.

Please follow these guidelines for branch management and workflow triggers to ensure a smooth development and release process.
