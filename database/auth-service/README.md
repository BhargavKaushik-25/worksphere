# Auth Service Database

Flyway migrations for the Authentication Service-owned PostgreSQL database
(`auth_db`).

## V1 assumptions

- The authentication identity table is named `auth_user`; this avoids the
  reserved and ambiguous `user` name while keeping all relationships local.
- User credentials use a BCrypt-compatible `password_hash`. Refresh tokens are
  represented only by a secure `token_hash`; the raw token is never persisted.
- `account_status` supports `ACTIVE`, `INACTIVE`, and `LOCKED`. Lock duration
  and failed-attempt policy remain application configuration.
- `login_attempt.user_id` is nullable so attempts for unknown email addresses
  can still be recorded. The email is retained for security monitoring.
- All tables use UUID identifiers and the common audit/soft-delete columns.
  Audit actor UUIDs are intentionally not foreign keys because user identity
  is also owned by this service and actors may be system-initiated.
- Foreign keys exist only between tables in this auth database. No roles,
  users, or organizations are referenced across service databases.
- Password reset, password history, MFA, durable session records, and a full
  audit-service model are deferred from this MVP foundation.

Apply migrations with the service's configured Flyway runner against
PostgreSQL 17 or a compatible PostgreSQL version.