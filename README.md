# Note Taking Application

## Building & Running the Application

At the project root directory, run the following command on Unix:

```bash
./mvnw spring-boot:run
```
or for Windows:
```powershell
mvnw.cmd spring-boot:run
```

This command starts the application on the default port (8080).

## API Endpoints

The application provides the following API endpoints:

- `GET /notes`: Returns a list of all notes.
- `GET /notes/{id}`: Returns the note with the given ID.
- `POST /notes`: Creates a new note. Both title & content must be provided in the request body, else gives appropriate error message.
- `PUT /notes/{id}`: Updates the note with the given ID. The new note details(title, content or both) should be provided in the request body.
- `DELETE /notes/{id}`: Deletes the note with the given ID.