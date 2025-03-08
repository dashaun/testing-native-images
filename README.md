## Testing your way to Production Confidence with Native Images
### DevNexus 2025

```bash
docker run --name testing-graalvm --rm -v $PWD/docs:/usr/share/nginx/html:ro -d -p 8088:80 nginx
```