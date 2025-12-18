# Notes of Wisdom

Una aplicación Android moderna para guardar y organizar notas, citas y pensamientos con referencias bibliográficas completas.

## Características

- **Crear y gestionar notas** con texto, autor y referencias
- **Categorizar fuentes** por tipo (Libro, Película, Video)
- **Referencias precisas** con números de página o timestamps
- **Eliminar notas** con confirmación
- **Editar notas** existentes
- **Compartir notas** con citas formateadas
- **Interfaz moderna** con Material Design 3 y Jetpack Compose

## Stack Tecnológico

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose
- **Arquitectura**: MVVM
- **Base de datos**: Room (SQLite)
- **Navegación**: Navigation Compose
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)

## Estructura del Proyecto

```
app/src/main/java/com/cesarforall/notesofwisdom/
├── data/               # Capa de datos
│   ├── database/      # Room database
│   ├── dao/           # Data Access Objects
│   └── repositories/  # Repositorios
├── ui/                # Capa de UI
│   ├── home/         # Pantalla principal
│   ├── noteForm/     # Formulario de notas
│   └── theme/        # Tema de la app
├── MainActivity.kt
└── NotesScreen.kt    # Navegación
```

## Cómo Ejecutar

### Requisitos
- Android Studio (versión reciente)
- JDK 11 o superior
- Android SDK con API 35

### Pasos

1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Esperar a que Gradle sincronice
4. Ejecutar en emulador o dispositivo físico

### Comandos Gradle

```bash
# Compilar APK debug
./gradlew assembleDebug

# Ejecutar tests
./gradlew test

# Instalar en dispositivo conectado
./gradlew installDebug
```

## Modelo de Datos

- **Note**: Almacena el texto, autor, fuente y referencia
- **SourceType**: Tipos de fuente (Libro, Película, Video)
- **NoteWithSourceType**: Relación completa nota-fuente

## Dependencias Principales

- Jetpack Compose (2024.09.00)
- Room Database (2.7.2)
- Navigation Compose (2.9.1)
- Material 3
- Lifecycle & ViewModel

## Licencia

MIT License
