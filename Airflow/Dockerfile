FROM astrocrpublic.azurecr.io/runtime:3.0-8

# Install Google Chrome and dependencies
USER root
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    gnupg \
    curl \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list' \
    && apt-get update && apt-get install -y google-chrome-stable \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install Python dependencies (from your requirements.txt)
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Switch back to astro user (non-root)
USER astro