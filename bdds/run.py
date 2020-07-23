import behave.__main__
import behave.configuration

behave.configuration.setup_parser().add_argument('-u', '--url', help='Address of service')

behave.__main__.main()
