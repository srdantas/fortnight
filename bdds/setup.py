import setuptools

setuptools.setup(
    name='bdd',
    packages=setuptools.find_packages(),
    include_package_data=True,
    install_requires=[
        'requests',
        'behave'
    ]
)
