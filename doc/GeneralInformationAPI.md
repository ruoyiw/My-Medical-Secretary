# General Information API

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=2 orderedList=false} -->
<!-- code_chunk_output -->
You can use this set of APIs to access the general information about the hospital, the doctor, the radiology and the pathology.
* [List doctor resources ( R, 304 )](#list-doctor-resources-r-304)
* [List hospital resources ( R, 304 )](#list-hospital-resources-r-304)
* [List pathology resources ( R, 304 )](#list-pathology-resources-r-304)
* [List radiology resources ( R, 304 )](#list-radiology-resources-r-304)

<!-- /code_chunk_output -->

## List doctor resources ( R, 304 )
**Note:** This API requires the token to be sent together with the resource url, as a part of the request header.

    GET /generalInformation/doctors

**Example Request:**
    
    GET http://localhost:8080/api/generalInformation/doctors
    
**Example Response:**

    HTTP/1.1 200 
    Content-Type: application/json
    
    
    [
      {
        "address": "66 Darebin Street, Heidelberg VIC 3084",
        "contact": "(03) 9458 5199",
        "email": "eception@66darebinst.com.au",
        "expertise": "Pancreatic cancer, gallbladder cancer",
        "id": 1,
        "name": "A/Prof Niall Tebbutt",
        "website": "www.darebinstspecialistcentre.com.au"
      },
      {
        "address": "267 Collins St, Melbourne VIC 3000",
        "contact": "(03) 9654 6088",
        "email": "Mehrdad@example.com",
        "expertise": "Rectal cancer",
        "id": 2,
        "name": "Mr Mehrdad Nikfarjam",
        "website": "https://collinsstmedicalcentre.com.au"
      }
    ]

## List hospital resources ( R, 304 )
**Note:** This API requires the token to be sent together with the resource url, as a part of the request header.

    GET /generalInformation/hospitals

**Example Request:**
    
    GET http://localhost:8080/api/generalInformation/hospitals
    
**Example Response:**
    
    HTTP/1.1 200 
    Content-Type: application/json
    
    
    [
      {
        "address": "14-20 Blackwood St, North Melbourne VIC 3195",
        "contact": "(03) 8373 7600",
        "fax": "(03) 9328 5803",
        "id": 1,
        "name": "Australian Prostate Centre",
        "type": "Cancer Treatment Center",
        "website": "australianprostatecentre.org.au"
      },
      {
        "address": "1/84 Bridge Rd, Richmond VIC 3121",
        "contact": "(03) 9421 6425",
        "fax": "(03) 9421 6372",
        "id": 2,
        "name": "Cancer Specialists",
        "type": "Cancer Treatment Center",
        "website": "cancerspecialists.com.au"
      }
    ]
    
## List pathology resources ( R, 304 )
**Note:** This API requires the token to be sent together with the resource url, as a part of the request header.

    GET /generalInformation/pathologies

**Example Request:**
    
    GET http://localhost:8080/api/generalInformation/pathologies
    
**Example Response:**

    HTTP/1.1 200 
    Content-Type: application/json
    
    
    [
      {
        "address": "265 Faraday St, Carlton VIC 3053",
        "contact": "(03) 9250 0300",
        "fax": "",
        "id": 1,
        "name": "VCS Pathology",
        "website": "http://www.vcspathology.org.au/"
      },
      {
        "address": "292 Swanston St, Melbourne VIC 3000",
        "contact": "(03) 9654 2214",
        "fax": "(03) 9650 9241",
        "id": 2,
        "name": "Melbourne Pathology",
        "website": "http://mps.com.au/"
      }
    ]
## List radiology resources ( R, 304 )
**Note:** This API requires the token to be sent together with the resource url, as a part of the request header.

    GET /generalInformation/radiologies

**Example Request:**
    
    GET http://localhost:8080/api/generalInformation/radiologies
    
**Example Response:**

    HTTP/1.1 200 
    Content-Type: application/json
    
    
    [
      {
        "address": "100 Victoria Parade, East Melbourne VIC 3002",
        "contact": "(03) 9667 1667",
        "fax": "(03) 9667 1666",
        "id": 1,
        "name": "Melbourne Radiology Clinic",
        "website": "melbourneradiology.com.au"
      },
      {
        "address": "25/292 Swanston St, Melbourne VIC 3000",
        "contact": "(03) 9639 7269",
        "fax": "",
        "id": 2,
        "name": "MIA Radiology",
        "website": "miaradiology.com.au"
      }
    ]
