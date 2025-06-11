# USOLO Project

### Authors

- Juan Sebastián Guerrero Lasso - A00395907
- Samuel Gutierrez Dominguez - A00381035
- Juan Sebastián Libreros García - A00379813
- Daniel Mejía Ruales - A00399267
- Santiago Valencia García - A00395902

## Roles and permissions that were used

The user role, which is public to anyone registering for the application, and the application's own user role were used. Create and read permissions were also provided for users.

## Sprint 2 Target

To carry out this sprint, the following functionalities and objectives were taken into account:

1. **Product Registration**

Allows sellers to add new products to the system by entering relevant information such as description, category, price, and availability.

2. **Product Update**

Facilitates the modification of previously registered product data, ensuring that the information remains up-to-date and accurate.

3. **Product Deletion**

Provides the option to remove a product from the catalog when it is no longer available or when you no longer want to offer it.

4. **Product Display**

Displays products available for rent, as well as those already rented, allowing for clear and organized inventory management.

5. **Reservation Screen (Initial Layout)**

Preliminary design of the interface where customers can select and reserve products, with no active functionality at this stage.

## Data used and how to execute the project

For this project, Directus was used as a backend-as-a-service tool. A PostgreSQL database was used. To run the project, follow these steps:

1. Upload the Docker container with the database, located in the root of the DirectusUpdated directory.

```bash
docker compose up -d
```

2. Run the database schema and necessary inserts with the test data, located in the 'scripts' folder within the DirectusUpdated directory.

```bash
./fromcontainerinit.sh
./fromhostinit.sh
./fromcontainerinit.bat // for Windows
./fromhostinit.bat // for Windows
```

3. Run the app inside Android Studio and enjoy the magic :D

### How to add images and associate them with a product (item) within the app?

To add an image and subsequently associate it with a product within the app, there are two options:

1. Create the product from the app and add its image during creation.
2. Manually add the image to Directus in the directus_files collection, then obtain the auto-generated ID for that image within Directus, and then assign that ID to the 'photo' field in the items table of data.sql. This can be done through the Directus graphical interface at http://localhost:8055, going to the files section and uploading the image, or it can be done by curling to a specific endpoint. For the second option, run the following requests to the following endpoints:

- Log in as the administrator to obtain the token:

```bash
curl -X POST http://localhost:8055/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "svalenciagarcia707@gmail.com",
    "password": "admin"
  }'
```
- Add the image with the absolute path and the token obtained when logging in with the administrator:

```bash
curl -X POST http://localhost:8055/files \
  -H "Authorization: Bearer ACCESS_TOKEN" \
  -F "file=@ABSOLUTE_ROUTE_OF_IMAGE"
```
- Check the IDs of uploaded images. You can do this from the Directus interface or by using the following command:

```bash
curl -X GET \
  http://localhost:8055/files \
  -H "Authorization: Bearer ACCESS_TOKEN"
```
- Relate that image ID to its corresponding product. To do this, edit the data.sql file located in the scripts subfolder of the DirectusUpdated folder, which is located within the project. Within each of the item table inserts, change the "photo" field with the auto-generated ID of each image.




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
