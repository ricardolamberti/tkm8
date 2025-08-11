# Paquete `pss.www`

Este módulo reúne los componentes web del framework **gsCrono** y se divide en dos grandes familias:

- `platform`: infraestructura de servidor para procesar solicitudes y gestionar el ciclo de vida de las aplicaciones.
- `ui`: biblioteca de componentes de interfaz de usuario reutilizables.

## Convenciones de subpaquetes

| Subpaquete                      | Propósito general |
|---------------------------------|------------------|
| `platform.actions`              | Acciones y *resolvers* que interpretan solicitudes web.
| `platform.applications`         | Contexto de aplicaciones, sesiones y servidor.
| `platform.content`, `tasks`, ...| Servicios auxiliares como generación de contenido o tareas.
| `ui.controller`                 | Coordinadores de UI y *front door*.
| `ui.processing`                 | Procesadores de solicitudes y coordinación de vistas.
| `ui.views`, `ui.layout`, `ui.skins` | Componentes visuales, layouts y temas.

## Dependencias principales

- `pss.core` y `pss.common`: capas de negocio y utilitarios base.
- `org.apache.cocoon.environment.Request`: acceso al entorno servlet.
- `com.google.gson`: serialización JSON para datos de acciones.

## Flujo típico de una solicitud web

```text
HTTP request
   │
   ▼
JWebActionRequestProcessor
   │ crea
   ▼
JWebRequest
   │ delega en
   ▼
JDoPssActionResolver
   │ produce
   ▼
JWebActionResult → componentes UI (`JWebView`, `JWebWinForm`, ...)
```

## Clases clave

- `JWebRequest`: encapsula los datos de la solicitud HTTP, maneja la sesión y coordina el procesamiento.
- `JDoPssActionResolver`: resuelve y ejecuta acciones de negocio dentro del flujo web.
- `ActionResolverStrategy` y resolvers concretos (`RedirectActionResolver`, `SubmitActionResolver`, ...):
  implementan un patrón *strategy* que decide cómo procesar cada `JAct` (redirección,
  navegación hacia atrás, envío, etc.).
- `JWebActionRequestProcessor`: interfaz que inicializa el contexto de aplicación y crea `JWebRequest`.
- `JWebApplication` / `JWebApplicationSession`: representan la aplicación y la sesión del usuario.
- `JWebView`, `JWebWinForm` y demás componentes en `ui` construyen la respuesta HTML.

Este README resume la finalidad de los subpaquetes y cómo interactúan para servir una solicitud web.
