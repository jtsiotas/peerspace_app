# Peerspace Clone: Hourly Space Rental Platform

Welcome to the repository for the **Peerspace Clone**, a comprehensive peer-to-peer marketplace platform that allows users to list, discover, and book unique spaces for events, meetings, and creative productions by the hour.

## 🚀 Overview

This platform connects **Hosts** (space owners) with **Guests** (tenants/event organizers). It handles the complete lifecycle of a space rental, from browsing and booking to messaging, payments, and post-event reviews. 

The system is built on a robust relational database schema designed to handle hourly availability, variable pricing, multi-role user accounts, and booking statuses.

## ✨ Key Features

* **Role-Based Access Control:** Users can be Owners (Hosts), Tenants (Guests), or Admins.
* **Property Management:** Hosts can list spaces with hourly rates, capacity constraints, square footage, geolocation, and categorized amenities.
* **Dynamic Booking System:** Supports hourly bookings, automatic calculation of host/guest fees, subtotal generation, and slot blocking.
* **Availability Management:** Hosts can block out specific time slots.
* **Integrated Messaging:** Real-time messaging contextually tied to specific bookings.
* **Payments & Refunds:** Tracks payment statuses, methods, and potential refunds.
* **Review System:** Two-way reviewing (guest reviewing space/host, host reviewing guest) linked to completed bookings.

## 🗄️ Database Schema

The core of the application is built around a carefully structured relational database. Below is the documentation of the core entities based on the ER Diagram:

### Users & Authorization
* **`USERS`**: Core user entity storing authentication details (`email`, `password`) and profile information.
* **`ROLES` & `USERS_ROLES`**: Manages permissions. Available roles are `OWNER`, `TENANT`, and `ADMIN`.

### Properties & Listings
* **`PROPERTIES`**: The central entity for spaces. Includes fields for `hourly_rate`, `min_hours`, `max_hours`, `capacity`, `size_sqm`, geolocation (`latitude`, `longitude`), and approval `status` (ACTIVE, INACTIVE, PENDING).
* **`PROPERTY_IMAGES`**: Stores image URLs for properties, including ordering and identifying the main display image.
* **`AMENITIES` & `PROPERTIES_AMENITIES`**: A many-to-many relationship linking properties to categorized amenities (e.g., WiFi, Projector, Sound System).
* **`BLOCKED_SLOTS`**: Allows hosts to block out specific date/time ranges when the property is unavailable for booking.

### Bookings & Transactions
* **`BOOKINGS`**: Tracks the reservation. Links a Guest to a Property. Contains crucial financial calculations: `property_rate`, `subtotal`, `guest_fee`, `host_fee`, `total_amount`, and `host_payout`. Manages booking state via `status` (pending, confirmed, completed, cancelled) and cancellation policies.
* **`PAYMENTS`**: Linked 1:1 with bookings to track the transaction state, `amount`, `currency`, `method`, and refund data.

### Communication & Feedback
* **`MESSAGES`**: In-app messaging system linking conversations directly to a specific `booking_id` for contextual communication between host and guest.
* **`REVIEWS`**: Stores post-booking feedback. Includes a `reviewee_role` to differentiate between a host reviewing a guest and vice-versa, along with a `rating` and `comment`.

---

## 🛠️ Tech Stack

This project is built using a modern, scalable full-stack architecture:

* **Database:** **PostgreSQL** (Ideal for relational data, complex joins for financial data, and geospatial queries for map search).
* **Backend API:** **Java & Spring Boot**
  * *Spring Web:* For RESTful API endpoints.
  * *Spring Data JPA / Hibernate:* For ORM and seamless database interactions.
  * *Spring Security:* For role-based access control (RBAC) and JWT authentication.
* **Frontend:** **React.js**
  * *State Management:* Context API or Redux (depending on project scale).
  * *Routing:* React Router DOM.
* **Storage:** AWS S3 or Cloudinary for handling `PROPERTY_IMAGES` uploads.
* **Payments:** Stripe API for marketplace payment routing.

---
