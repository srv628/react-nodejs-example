# Build React application
FROM node:18-alpine AS ui-build
WORKDIR /app/my-app
COPY my-app/package*.json ./
RUN npm install
COPY my-app/ ./
# Add environment variable to fix OpenSSL issues with newer Node versions
ENV NODE_OPTIONS="--openssl-legacy-provider"
RUN npm run build

# Build API server
FROM node:18-alpine AS server-build
WORKDIR /app/api
COPY api/package*.json ./
RUN npm install --only=production
COPY api/ ./

# Create final image
FROM node:22
WORKDIR /app
# Copy built React app from ui-build stage
COPY --from=ui-build /app/my-app/build /app/my-app/build
# Copy API files from server-build stage
COPY --from=server-build /app/api /app/api

# Set proper permissions for security
#RUN addgroup -S appgroup && adduser -S appuser -G appgroup
#RUN chown -R appuser:appgroup /app
RUN addgroup --system appgroup \
    && adduser --system --ingroup appgroup appuser

USER appuser

# Expose the API port
EXPOSE 3080

# Start the API server
CMD ["node", "./api/server.js"]
