# USOLO Project

### Authors

- Juan Sebastián Guerrero Lasso - A00395907
- Samuel Gutierrez Dominguez - A00381035
- Juan Sebastián Libreros García - A00379813
- Daniel Mejía Ruales - A00399267
- Santiago Valencia García - A00395902

## Roles and permissions that were used

The user role, which is public to anyone registering for the application, and the application's own user role were used. Create and read permissions were also provided for users.

## Data used and how to execute the project

For this project, Directus was used as a backend-as-a-service tool. A PostgreSQL database was used. To run the project, follow these steps:

1. Upload the Docker container with the database, located in the root of the Directus directory.

```bash
docker compose up -d
```

2. Run the database schema and necessary inserts with the test data, located in the 'scripts' folder within the Directus directory.

```bash
./fromcontainerinit.sh
./fromhostinit.sh
./fromcontainerinit.bat // for Windows
./fromhostinit.bat // for Windows
```

3. Run the app inside Android Studio and enjoy the magic :D

### Commit Structure

```

type: short description

[optional body]

[optional footer]

```

### Types

- `feat`: A new feature.
- `fix`: A bug fix.
- `docs`: Documentation changes.
- `style`: Formatting or linting changes.
- `refactor`: Code changes that neither fix a bug nor add a feature.
- `test`: Adding or updating tests.
- `chore`: Maintenance tasks.
- `build`: Changes to the build system or dependencies.
- `ci`: Changes to CI/CD configurations.
- `perf`: Performance improvements.
- `revert`: Reverts a previous commit.

### Examples

- `feat: add user authentication`
- `fix: resolve null pointer exception in user endpoint`
- `docs: update readme with installation instructions`

### Best Practices

1. Write commit messages in ****lowercase****.

2. Keep the short description under ****50 characters****.

3. Use the body to explain the ****what**** and ****why**** of the change.

4. Reference issues or tickets in the footer (e.g., `closes #123`).