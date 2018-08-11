# Appointment API

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=2 orderedList=false} -->
<!-- code_chunk_output -->

* [List your appointment](#list-your-appointment)
* [List user's appointments ( R, 304 )](#list-users-appointments-r-304)
* [Get an appointment ( R, 304 )](#get-an-appointment-r-304)
* [Update appointment note ( U )](#update-appointment-note-u)
* [appointment note ( R )](#appointment-note-r)
* [appointment note ( D )](#appointment-note-d)
* [appointment confirmation ( U )](#appointment-confirmation-u)
* [File (TBC)](#file-tbc)

<!-- /code_chunk_output -->

## List your appointment
List appointments of the authenticated user

    GET /me/appointments

### Parameters

Name  | Type  | Description
----- | ----- | -----------
`since`  | `Date`    | `Optional`. Represented as a string with a format of `YYYY-MM-DD`.
`to` |	`Date`    | `Optional`. Represented as a string with a format of `YYYY-MM-DD`.
`is_confirmed` | `boolean` | `Optional`. Specify whether the appointment is confirmed.

### Response

## List user's appointments ( R, 304 )

List appointments of the authenticated user

    GET /users/:user/appointments

## Get an appointment ( R, 304 )

    GET /appointments/:appointment

## Update appointment note ( U )

    POST /appointments/:appointment/usernote

## appointment note ( R )

    GET /appointments/:appointment/usernote

## appointment note ( D )

    DELETE /appointments/:appointment/usernote

## appointment confirmation ( U )

    POST /appointments/:appointment/confirm

## File (TBC)
